package hu.erste.slacct.codetables.integration.config.sftp;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import hu.erste.slacct.codetables.integration.config.ProcessedPayload;
import hu.erste.slacct.codetables.integration.filter.FileNameMatcher;
import hu.erste.slacct.codetables.integration.routing_table.service.FileProcessor;
import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.filters.RegexPatternFileListFilter;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.advice.ExpressionEvaluatingRequestHandlerAdvice;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.sftp.dsl.Sftp;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

import java.io.File;
import java.nio.charset.Charset;


@Configuration
@ConditionalOnProperty(name = "integration.sftp.source.host")
@RequiredArgsConstructor
public class SftpFlowConfig {

    final SftpConfigProperties properties;
    final FileProcessor fileProcessor;
    final QueueChannel fileWritingSuccessChannel;
    final QueueChannel fileWritingFaultyChannel;
    final UploadSuccessGateway uploadSuccessGateway;
    final UploadFaultyGateway uploadFaultyGateway;


    @Bean
    QueueChannel toSftpSuccessChannel() {
        return new QueueChannel();
    }

    @Bean
    public SessionFactory<LsEntry> sftpSessionFactory() {
        SftpConfigProperties.Source source = properties.getSource();
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(source.getHost());
        factory.setPort(source.getPort());
        factory.setUser(source.getUser());
        factory.setPassword(source.getPassword());
        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<>(factory);
    }


    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(100).get();
    }


    @Bean
    public IntegrationFlow sftpInboundFlow() {
        return IntegrationFlows.from(
                Sftp.inboundAdapter(sftpSessionFactory())
                        .preserveTimestamp(true)
                        .remoteDirectory(properties.getSource().getInputDir())
                        .deleteRemoteFiles(true)
                        .filterFunction(source -> FileNameMatcher.of(source.getFilename()).matches())
                        .localDirectory(new File(properties.getTempDir()))
                        .localFilter(new RegexPatternFileListFilter(FileNameMatcher.REGEX))
                        .autoCreateLocalDirectory(true)
                        .maxFetchSize(1),
                configurer -> configurer.poller(Pollers.fixedDelay(properties.getDelay(), 1000)).autoStartup(true))

                .<File>handle((payload, headers) -> new ProcessedPayload(payload, fileProcessor.process(payload, Charset.forName(properties.getCharset()))))

                .<ProcessedPayload, Boolean>route(
                        ProcessedPayload::success,
                        r -> r
                                .subFlowMapping(true, sf -> sf
                                        .channel(c -> c.queue(10))
                                        .publishSubscribeChannel(c -> c
                                                .subscribe(cf -> cf
                                                        .<ProcessedPayload>handle((payload, headers) -> payload.getFile())
                                                        .handle((payload, headers) -> {
                                                            uploadSuccessGateway.upload((File) payload);
                                                            return payload;
                                                        })
                                                        .wireTap(fileWritingSuccessChannel)
                                                ))
                                )
                                .subFlowMapping(false, sf -> sf
                                        .channel(c -> c.queue(10))
                                        .publishSubscribeChannel(c -> c
                                                .subscribe(cf -> cf
                                                        .<ProcessedPayload>handle((payload, headers) -> {
                                                            uploadFaultyGateway.upload(payload.getFile());
                                                            return payload;
                                                        })
                                                        .<ProcessedPayload>handle((payload, headers) -> {
                                                            uploadFaultyGateway.upload(payload.getErrorFile());
                                                            return payload;
                                                        })
                                                        .wireTap(fileWritingFaultyChannel)
                                                )
                                        )
                                )
                )
                .get();
    }


    @MessagingGateway
    private interface UploadSuccessGateway {
        @Gateway(requestChannel = "sftpOutboundSuccessFlow.input")
        void upload(File file);
    }

    @Bean
    public IntegrationFlow sftpOutboundSuccessFlow() {
        return f -> f
                .handle(
                        Sftp.outboundAdapter(sftpSessionFactory(), FileExistsMode.REPLACE)
                                .useTemporaryFileName(false)
                                .autoCreateDirectory(true)
                                .remoteDirectory(properties.getSource().getSuccessOutputDir()),
                        c -> c.advice(expressionAdvice())
                );
    }


    @MessagingGateway
    private interface UploadFaultyGateway {
        @Gateway(requestChannel = "sftpOutboundFaultyFlow.input")
        void upload(File file);
    }

    @Bean
    public IntegrationFlow sftpOutboundFaultyFlow() {
        return f -> f
                .handle(
                        Sftp.outboundAdapter(sftpSessionFactory(), FileExistsMode.REPLACE)
                                .useTemporaryFileName(false)
                                .autoCreateDirectory(true)
                                .remoteDirectory(properties.getSource().getFaultyOutputDir()),
                        c -> c.advice(expressionAdvice())
                );
    }


    @Bean
    public Advice expressionAdvice() {
        ExpressionEvaluatingRequestHandlerAdvice advice = new ExpressionEvaluatingRequestHandlerAdvice();
        advice.setOnSuccessExpressionString("payload.delete()");
        advice.setOnFailureExpressionString("payload + ' failed to upload'");
        advice.setTrapException(true);
        return advice;
    }
}

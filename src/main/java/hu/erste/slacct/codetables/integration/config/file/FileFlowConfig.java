package hu.erste.slacct.codetables.integration.config.file;

import hu.erste.slacct.codetables.integration.config.ProcessedPayload;
import hu.erste.slacct.codetables.integration.filter.FileNameMatcher;
import hu.erste.slacct.codetables.integration.routing_table.service.FileProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.nio.charset.Charset;


@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "integration.file.input-dir")
public class FileFlowConfig {


    final FileConfigProperties properties;
    final FileProcessor fileProcessor;
    final QueueChannel fileWritingSuccessChannel;
    final QueueChannel fileWritingFaultyChannel;
    final UploadErrorGateway uploadErrorGateway;


    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(properties.getInputDir()));
        return messageSource;
    }

    @Bean
    public MessageHandler toTempDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(properties.getTempDir()));
        handler.setDeleteSourceFiles(true);
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        return handler;
    }

    @Bean
    public MessageHandler toSuccessDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(properties.getSuccessOutputDir()));
        handler.setDeleteSourceFiles(true);
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        return handler;
    }

    @Bean
    public MessageHandler toFaultyDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(properties.getFaultyOutputDir()));
        handler.setDeleteSourceFiles(true);
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        return handler;
    }


    @Bean
    public GenericSelector<File> filter() {
        return source -> FileNameMatcher.of(source.getName()).matches();
    }


    @Bean
    public IntegrationFlow fileProcessFlow() {
        return IntegrationFlows.from(sourceDirectory(), configurer -> configurer.poller(Pollers.fixedDelay(properties.getDelay())))
                .filter(filter())
                .handle(toTempDirectory())

                .<File>handle((payload, headers) -> new ProcessedPayload(payload, fileProcessor.process(payload, Charset.forName(properties.getCharset()))))

                .<ProcessedPayload, Boolean>route(
                        ProcessedPayload::success,
                        r -> r
                                .subFlowMapping(true, sf -> sf
                                        .<ProcessedPayload>handle((payload, headers) -> payload.getFile())
                                        .handle(toSuccessDirectory())
                                        .gateway(fileWritingSuccessChannel))
                                .subFlowMapping(false, sf -> sf
                                        .gateway(
                                                gf -> gf
                                                        .<ProcessedPayload>handle((payload, headers) -> {
                                                            uploadErrorGateway.upload(payload.getFile());
                                                            return payload;
                                                        })
                                                        .<ProcessedPayload>handle((payload, headers) -> {
//                                                            uploadErrorGateway.upload(payload.getErrorFile());
                                                            return payload;
                                                        })
                                                        .gateway(fileWritingFaultyChannel)
                                        ))
                )
                .get();
    }


    @Bean
    public IntegrationFlow fileOutboundFaultyFlow() {
        return f -> f.handle(toFaultyDirectory());
    }


    @MessagingGateway
    private interface UploadErrorGateway {
        @Gateway(requestChannel = "sftpOutboundFaultyFlow.input")
        void upload(File file);
    }
}

package hu.erste.slacct.codetables.integration.config;

import hu.erste.slacct.codetables.integration.config.file.FileConfigProperties;
import hu.erste.slacct.codetables.integration.config.sftp.SftpConfigProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;


@Configuration
@ComponentScan("hu.erste.slacct.codetables.integration")
@EnableConfigurationProperties({FileConfigProperties.class, SftpConfigProperties.class})
@EnableIntegration
@EnableJpaRepositories(basePackages = {"hu.erste.slacct.codetables.integration.**.repository"})
@EntityScan("hu.erste.slacct.codetables.integration")
@IntegrationComponentScan
public class Config {

    @Bean
    public QueueChannel fileWritingSuccessChannel() {
        return new QueueChannel();
    }

    @Bean
    public QueueChannel fileWritingFaultyChannel() {
        return new QueueChannel();
    }
}

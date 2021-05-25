package hu.erste.slacct.codetables.integration.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.QueueChannel;

@EnableAutoConfiguration
public class TestConfig {

    @Bean
    public QueueChannel testChannel() {
        return new QueueChannel();
    }
}

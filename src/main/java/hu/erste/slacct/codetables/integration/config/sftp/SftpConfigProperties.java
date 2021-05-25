package hu.erste.slacct.codetables.integration.config.sftp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "integration.sftp", ignoreUnknownFields = false)
@Data
public class SftpConfigProperties {

    long delay = 2000;
    String charset;
    String tempDir;
    Source source;

    @Data
    public static class Source {
        String host;
        int port = 22;
        String user;
        String password;
        String inputDir;
        String successOutputDir;
        String faultyOutputDir;
    }
}

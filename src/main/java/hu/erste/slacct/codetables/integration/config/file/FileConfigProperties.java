package hu.erste.slacct.codetables.integration.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "integration.file", ignoreUnknownFields = false)
@Data
public class FileConfigProperties {

    long delay = 2000;
    String charset;
    String inputDir;
    String tempDir;
    String successOutputDir;
    String faultyOutputDir;
}

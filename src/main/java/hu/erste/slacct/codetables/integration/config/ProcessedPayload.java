package hu.erste.slacct.codetables.integration.config;

import lombok.Value;

import java.io.File;

@Value
public class ProcessedPayload {
    File file;
    File errorFile;

    public boolean success() {
        return errorFile == null;
    }
}

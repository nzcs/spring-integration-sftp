package hu.erste.slacct.codetables.integration.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(makeFinal = true)
public class FileUtil {

    String path;
    String fileName;

    public File withContent(Object content) throws IOException {
        return Files.write(Paths.get(path, fileName), parse(content)).toFile();
    }

    private byte[] parse(Object content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(content);
    }
}

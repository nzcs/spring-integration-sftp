package hu.erste.slacct.codetables.integration;

import hu.erste.slacct.codetables.integration.config.Config;
import hu.erste.slacct.codetables.integration.config.TestConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {Config.class, TestConfig.class}
)
@ActiveProfiles("test")
public abstract class AbstractTest {

    protected void delete(String directory) throws IOException {
        Path path = Paths.get(directory);
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .peek(System.out::println)
                    .forEach(File::delete);
        }
    }
}

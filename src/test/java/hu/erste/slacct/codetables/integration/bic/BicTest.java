package hu.erste.slacct.codetables.integration.bic;

import hu.erste.slacct.codetables.integration.AbstractTest;
import hu.erste.slacct.codetables.integration.bic.service.BicFileProcessor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BicTest extends AbstractTest {

    @Autowired
    BicFileProcessor fileProcessor;


    @Test
    public void test() {
        Path path = Paths.get("src/test/resources/data/BANKDIRECTORYPLUS_V2_FULL_20190628_Test.txt");

        fileProcessor.process(path.toFile(), UTF_8);
    }
}

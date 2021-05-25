package hu.erste.slacct.codetables.integration.file;

import hu.erste.slacct.codetables.integration.AbstractTest;
import hu.erste.slacct.codetables.integration.config.file.FileConfigProperties;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;


@ActiveProfiles("test-file")
@DirtiesContext(methodMode = BEFORE_METHOD)
public abstract class AbstractFileTest extends AbstractTest {

    @Autowired
    FileConfigProperties properties;


    @After
    public void after() throws IOException {
        delete(properties.getInputDir());
        delete(properties.getTempDir());
        delete(properties.getSuccessOutputDir());
        delete(properties.getFaultyOutputDir());
    }
}

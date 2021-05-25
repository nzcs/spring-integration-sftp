package hu.erste.slacct.codetables.integration.sftp;

import hu.erste.slacct.codetables.integration.AbstractTest;
import hu.erste.slacct.codetables.integration.config.sftp.SftpConfigProperties;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;


@ActiveProfiles("test-sftp")
@DirtiesContext(methodMode = BEFORE_METHOD)
public abstract class AbstractSftpTest extends AbstractTest {

    @Autowired
    SftpConfigProperties properties;


    @After
    public void after() throws IOException {
        delete(properties.getTempDir());
    }
}

package hu.erste.slacct.codetables.integration.file;

import hu.erste.slacct.codetables.integration.TestRule_HT202001_V12;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static hu.erste.slacct.codetables.integration.TestRule_HT202001_V12.FILE_NAME;
import static hu.erste.slacct.codetables.integration.TestRule_HT202001_V12.FILE_PATH;
import static org.hamcrest.Matchers.equalTo;


public class FileTest extends AbstractFileTest {

    @Autowired
    QueueChannel fileWritingSuccessChannel;
    @Autowired
    TestRule_HT202001_V12 testRule_ht202001_v12;


    @Test
    public void testSuccessFlow() throws IOException, InterruptedException {
        Files.copy(Paths.get(FILE_PATH + FILE_NAME), Paths.get(properties.getInputDir() + FILE_NAME));
        Thread.sleep(100);

        Message<?> message = fileWritingSuccessChannel.receive(10000);

        assert message != null;
        Assert.assertThat(message.getPayload(), equalTo(Paths.get(properties.getSuccessOutputDir() + FILE_NAME).toFile()));

        testRule_ht202001_v12.check();
    }
}

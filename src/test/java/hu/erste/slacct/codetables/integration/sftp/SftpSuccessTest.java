package hu.erste.slacct.codetables.integration.sftp;

import com.github.stefanbirkner.fakesftpserver.rule.FakeSftpServerRule;
import hu.erste.slacct.codetables.integration.TestRule_HT202001_V12;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static hu.erste.slacct.codetables.integration.TestRule_HT202001_V12.FILE_NAME;
import static hu.erste.slacct.codetables.integration.TestRule_HT202001_V12.FILE_PATH;


public class SftpSuccessTest extends AbstractSftpTest {

    @Rule
    public final FakeSftpServerRule sftpServer = new FakeSftpServerRule()
            .setPort(2244)
            .addUser("user", "pass");
    @Autowired
    QueueChannel fileWritingSuccessChannel;
    @Autowired
    TestRule_HT202001_V12 testRule_ht202001_v12;


    @Test
    public void test() throws IOException, InterruptedException {
        Path path = Paths.get(FILE_PATH + FILE_NAME);
        sftpServer.putFile(properties.getSource().getInputDir() + FILE_NAME, new FileInputStream(path.toFile()));

        Thread.sleep(100);
        assert sftpServer.existsFile(properties.getSource().getInputDir() + FILE_NAME);


        Message<?> message = fileWritingSuccessChannel.receive(10000);
        assert message != null;

        assert sftpServer.existsFile(properties.getSource().getSuccessOutputDir() + FILE_NAME);

        testRule_ht202001_v12.check();
    }
}

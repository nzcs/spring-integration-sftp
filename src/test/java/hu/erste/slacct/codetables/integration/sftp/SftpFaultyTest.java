package hu.erste.slacct.codetables.integration.sftp;

import com.github.stefanbirkner.fakesftpserver.rule.FakeSftpServerRule;
import hu.erste.slacct.codetables.integration.TestRule_HT202001_V13_Error;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static hu.erste.slacct.codetables.integration.TestRule_HT202001_V13_Error.ERROR_FILE_NAME;
import static hu.erste.slacct.codetables.integration.TestRule_HT202001_V13_Error.FILE_NAME;
import static hu.erste.slacct.codetables.integration.TestRule_HT202001_V13_Error.FILE_PATH;


public class SftpFaultyTest extends AbstractSftpTest {

    @Rule
    public final FakeSftpServerRule sftpServer = new FakeSftpServerRule()
            .setPort(2244)
            .addUser("user", "pass");
    @Autowired
    QueueChannel fileWritingFaultyChannel;
    @Autowired
    TestRule_HT202001_V13_Error testRule_ht202001_v13_error;


    @Test
    public void test() throws IOException, InterruptedException {
        Path path = Paths.get(FILE_PATH + FILE_NAME);
        sftpServer.putFile(properties.getSource().getInputDir() + FILE_NAME, new FileInputStream(path.toFile()));

        Thread.sleep(100);
        assert sftpServer.existsFile(properties.getSource().getInputDir() + FILE_NAME);


        Message<?> message = fileWritingFaultyChannel.receive(10000);
        assert message != null;

        assert sftpServer.existsFile(properties.getSource().getFaultyOutputDir() + FILE_NAME);
        assert sftpServer.existsFile(properties.getSource().getFaultyOutputDir() + ERROR_FILE_NAME);

        byte[] content = sftpServer.getFileContent(properties.getSource().getFaultyOutputDir() + ERROR_FILE_NAME);

        testRule_ht202001_v13_error.checkContent(content);
        testRule_ht202001_v13_error.checkDb();
    }
}

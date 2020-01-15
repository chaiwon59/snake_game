package snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import java.io.IOException;
import java.sql.SQLException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailSenderTest {
    private transient GreenMail smtp;
    private transient JavaMailSenderImpl sender;
    private transient EmailSender senderClass;
    private transient Dao dao;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        ServerSetup[] serverSetup = {
            new ServerSetup(3000, "localhost", "smtp"), ServerSetup.IMAP};

        this.smtp = new GreenMail(serverSetup);
        this.smtp.start();

        this.sender = spy(new JavaMailSenderImpl());

        sender.setPort(3000);
        sender.setHost("localhost");

        this.dao = mock(Dao.class);
        doReturn(true).when(dao).checkExistentUsername("Username");
        doReturn("email").when(dao).getEmail("Username");

        this.senderClass = spy(new EmailSender(this.dao, "Username"));
        this.senderClass.dao = dao;
        this.senderClass.sender = sender;
    }

    @AfterEach
    public void tearDown() {
        smtp.stop();
    }

    @Test
    public void checkUserNonExistingUser() {
        doReturn(false).when(dao).checkExistentUsername(any(String.class));

        assertFalse(senderClass.checkExistentUsername());
    }

    @Test
    public void checkUserExistingUser() {
        doReturn(true).when(dao).checkExistentUsername(any(String.class));

        assertTrue(senderClass.checkExistentUsername());
    }

    @Test
    public void testSendMessage() throws IOException, MessagingException {
        doReturn(true).when(dao).checkExistentUsername(any(String.class));

        senderClass.sendMessage();

        Message message = smtp.getReceivedMessages()[0];
        assertEquals(message.getSubject(), "Password Reset");
        assertTrue(GreenMailUtil.getBody(message)
                .replaceAll("=\r?\n", "")
                .contains("After you login, you will be prompted to enter a new password"));

        verify(dao, times(1)).updatePassword(
                any(String.class), any(String.class), any(boolean.class));
        verify(sender, times(1)).send(any(MimeMessage.class));
        verify(senderClass, times(1)).createMessage();
    }

    @Test
    public void testCreateRandomPassword() {
        assertTrue(EmailSender.createRandomPassword().length() == 15);
    }
}

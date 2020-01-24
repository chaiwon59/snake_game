package snake;

import com.badlogic.gdx.Gdx;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.MessagingException;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//Seems to be a false positive, persists even after changing signature of the method
@SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
public class EmailSender implements Runnable {
    static final transient String email = "SnakeSEM8@gmail.com";
    static final transient String password = "passwordSnake";

    static final transient String subject = "Password Reset";
    static transient String randomPassword = createRandomPassword();

    transient JavaMailSenderImpl sender;
    transient Dao dao;

    transient String username;
    transient String to;

    transient EmailCreator creator;

    /**
     * Create instance of the email sender class.
     *
     * @param username username of the user to which the email is sent.
     */
    public EmailSender(Dao dao, String username) {
        this.sender = createSender();
        this.dao = dao;
        this.username = username;

        if (checkExistentUsername()) {
            try {
                to = dao.getEmail(username);
            } catch (SQLException e) {
                Gdx.app.exit();
            }
        }
    }

    /**
     * Checks whether the given user is in the database.
     *
     * @return true if user is in the system, false is user is not in the system.
     */
    public boolean checkExistentUsername() {
        return dao.checkExistentUsername(username);
    }

    /**
     * Sends the actual email to the user.
     */
    public void sendMessage() {
        randomPassword = createRandomPassword();
        creator = new EmailCreator(username, randomPassword);
        dao.updatePassword(username, randomPassword, true);

        try {
            sender.send(creator.createMessage(to));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sendMessage();
    }

    /**
     * Creates random password.
     *
     * @return random string of length 15.
     */
    public static String createRandomPassword() {
        return RandomString.make(15);
    }

    /**
     * Method for creating the sender of the email.
     *
     * @return email sender.
     */
    public JavaMailSenderImpl createSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);

        sender.setUsername(email);
        sender.setPassword(password);

        Properties properties = sender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return sender;
    }
}

package snake;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

    /**
     * Create instance of the email sender class.
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
        dao.updatePassword(username, randomPassword, true);

        try {
            sender.send(createMessage());
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sendMessage();
    }

    /**
     * Creates random password.
     * @return random string of length 15.
     */
    public static String createRandomPassword() {
        return RandomString.make(15);
    }

    /**
     * Creates the email to send to the user.
     *
     * @return email to the user
     * @throws IOException        if server cannot be reached
     * @throws MessagingException if message cannot be created
     */
    public MimeMessage createMessage() throws IOException, MessagingException {
        BodyPart message = createBodyPart();
        Multipart multi = createMultipart(message);

        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);

        MimeMessage mail = new MimeMessage(session);
        mail.setFrom(new InternetAddress(email));
        mail.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mail.setSubject(subject);
        mail.setContent(multi);

        return mail;
    }

    /**
     * Creates the body part of the message.
     * @return body part with the email text.
     * @throws MessagingException if the message cannot be created.
     */
    public BodyPart createBodyPart() throws MessagingException {
        BodyPart message = new MimeBodyPart();
        String text = getEmailText();

        message.setText(text);
        message.setHeader("Content-Type", "text");

        return message;
    }

    /**
     * Creates the multi part necessary for the email.
     * @param message body part of the email
     * @return multipart with the given bodypart
     * @throws MessagingException if the message cannot be created.
     */
    public Multipart createMultipart(BodyPart message) throws MessagingException {
        Multipart multi = new MimeMultipart();
        multi.addBodyPart(message);

        return multi;
    }

    /**
     * Creates the email text with the given username and new password.
     * @return email text
     */
    public String getEmailText() {
        String text = "Hey {Username},\n"
                + "\n"
                + "Here is your new password: {Password}\n"
                + "After you login, you will be prompted to enter a new password,"
                + " please do so as soon as possible.\n"
                + "\n"
                + "Hope you will continue to enjoy playing our game!";

        return text.replace("{Username}", username)
                .replace("{Password}", randomPassword);
    }


    /**
     * Method for creating the sender of the email.
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

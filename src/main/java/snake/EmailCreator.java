package snake;

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

public class EmailCreator {
    transient String username;
    transient String randomPassword;

    public EmailCreator(String username, String password) {
        this.username = username;
        this.randomPassword = password;
    }

    /**
     * Creates the email to send to the user.
     *
     * @return email to the user
     * @throws MessagingException if message cannot be created
     */
    public MimeMessage createMessage(String to) throws MessagingException {
        BodyPart message = createBodyPart();
        Multipart multi = createMultipart(message);

        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);

        MimeMessage mail = new MimeMessage(session);
        mail.setFrom(new InternetAddress(EmailSender.email));
        mail.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mail.setSubject(EmailSender.subject);
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
}

package ejb;

import java.security.MessageDigest;
import java.util.Properties;
import java.util.Random;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class PasswordRetrieval implements PasswordRetrievalRemote {

    @Override
    public String generateRandomString(Random random, String text, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(text.charAt(random.nextInt(text.length())));
        }
        return sb.toString();
    }

    @Override
    public boolean sendMailToUser(String emailaddress, String generatedPassword) {
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailaddress));
            message.setSubject("ME-pizza new password");
            String content = "Someone recently inquired about a new password connected to this email";
            message.setContent(content, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "mepizzacontact@gmail.com", "TrialAndError13");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    @Override
    public String hashPassword(String password) {
        byte[] passwordDigested = null;
        try {
            MessageDigest usernameMD = MessageDigest.getInstance("MD5");
            usernameMD.update(password.getBytes("UTF-8"));
            passwordDigested = usernameMD.digest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return DatatypeConverter.printHexBinary(passwordDigested);
    }

}

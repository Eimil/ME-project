package logics;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class ConfirmationSender implements ConfirmationSenderLocal {

    @Override
    public boolean sendConfirmation(String emailaddress, String[] data) {
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailaddress));
            message.setSubject("Booking confirmation");
            String content = "Your booking was completed, add data here";
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
}

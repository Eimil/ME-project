package logics;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
            Multipart multiPart = new MimeMultipart("alternative");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailaddress));
            message.setSubject("ME-pizza beställningsnummer : ");
            String content = "Din order med nummer # har nu lagts";
            MimeBodyPart body = new MimeBodyPart();
            body.setText(content, "utf-8");
            multiPart.addBodyPart(body);
            message.setContent(multiPart);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "mepizzacontact@gmail.com", "TrialAndError13");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Something went wrong with sending the passwordmail " + ex);
        }
        return false;
    }
}

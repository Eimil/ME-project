package logics;

import hibernate.HibernateUtil;
import hibernate.User;
import java.security.MessageDigest;
import java.util.Properties;
import java.util.Random;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class PasswordRetrieval implements PasswordRetrievalLocal {

    @Override
    public String generateRandomString(String text, int length) {
        Random random = new Random();
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
            Multipart multiPart = new MimeMultipart("alternative");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailaddress));
            message.setSubject("ME-pizza nytt lösenord");
            String content = "Någon har efterfrågat ett nytt lösenord för det här mailet. \n Det nya lösenordet är: " + generatedPassword;
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

    @Override
    public String hashPassword(String password) {
        byte[] passwordDigested = null;
        try {
            MessageDigest usernameMD = MessageDigest.getInstance("SHA-256");
            usernameMD.update(password.getBytes("UTF-8"));
            passwordDigested = usernameMD.digest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return DatatypeConverter.printHexBinary(passwordDigested);
    }

    @Override
    public boolean setNewPassword(String newPassword, String email) {
        try {
            org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            System.out.println("BEFORE USER");
            User user = (User) session.createQuery("select user from User user where user.email = :email")
                    .setParameter("email", email)
                    .uniqueResult();
            session.getTransaction().commit();
            System.out.println("GOT THE USER MATE");
            session.beginTransaction();
            user.setPassword(newPassword);
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Something went wrong with changing password " + ex);
        }
        return false;
    }
}

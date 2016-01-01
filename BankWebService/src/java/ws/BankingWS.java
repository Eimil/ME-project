package ws;

import hibernate.Card;
import hibernate.HibernateUtil;
import java.util.Properties;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
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
@WebService(serviceName = "BankingWS")
public class BankingWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "checkAccountBalance")
    public String checkAccountBalance(@WebParam(name = "price") int price, @WebParam(name = "cardNmbr") String cardNmbr, @WebParam(name = "csv") int csv) {
        // Balance är en double
        org.hibernate.Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Card card = (Card) session.createQuery("select card from Card card where card.cardNmbr = :cardNmbr and card.csv = :csv")
                    .setParameter("cardNmbr", cardNmbr).setParameter("csv", csv)
                    .uniqueResult();
            session.getTransaction().commit();
            if (card.getCardNmbr() != null) {
                if (card.getBalance() >= price) {
                    // Simulera kontotransaktion
                    session.beginTransaction();
                    card.setBalance(card.getBalance() - price);
                    session.getTransaction().commit();
                    session.close();
                    return "Transaction complete";
                } else {
                    session.close();
                    return "Not enough money";
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception in checking account balance : " + ex);
        }
        if (session != null) {
            session.close();
        }
        return "error";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "sendMailToAccount")
    public boolean sendMailToAccount(@WebParam(name = "address") String address, @WebParam(name = "orderInfo") String orderInfo, @WebParam(name = "orderNumber") String orderNumber) {
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            Multipart multiPart = new MimeMultipart("alternative");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
            message.setSubject("Din ME-pizza beställning");
            String content = "Din beställning med ordernummer " + orderNumber + " är nu gjord. \n" + "Order info : " + orderInfo;
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
            System.out.println("Something went wrong with sending the ordermail " + ex);
        }
        return false;
    }
}

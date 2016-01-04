
package ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CheckAccountBalanceResponse_QNAME = new QName("http://ws/", "checkAccountBalanceResponse");
    private final static QName _CheckAccountBalance_QNAME = new QName("http://ws/", "checkAccountBalance");
    private final static QName _SendMailsResponse_QNAME = new QName("http://ws/", "sendMailsResponse");
    private final static QName _SendMails_QNAME = new QName("http://ws/", "sendMails");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CheckAccountBalanceResponse }
     * 
     */
    public CheckAccountBalanceResponse createCheckAccountBalanceResponse() {
        return new CheckAccountBalanceResponse();
    }

    /**
     * Create an instance of {@link CheckAccountBalance }
     * 
     */
    public CheckAccountBalance createCheckAccountBalance() {
        return new CheckAccountBalance();
    }

    /**
     * Create an instance of {@link SendMailsResponse }
     * 
     */
    public SendMailsResponse createSendMailsResponse() {
        return new SendMailsResponse();
    }

    /**
     * Create an instance of {@link SendMails }
     * 
     */
    public SendMails createSendMails() {
        return new SendMails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckAccountBalanceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "checkAccountBalanceResponse")
    public JAXBElement<CheckAccountBalanceResponse> createCheckAccountBalanceResponse(CheckAccountBalanceResponse value) {
        return new JAXBElement<CheckAccountBalanceResponse>(_CheckAccountBalanceResponse_QNAME, CheckAccountBalanceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckAccountBalance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "checkAccountBalance")
    public JAXBElement<CheckAccountBalance> createCheckAccountBalance(CheckAccountBalance value) {
        return new JAXBElement<CheckAccountBalance>(_CheckAccountBalance_QNAME, CheckAccountBalance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "sendMailsResponse")
    public JAXBElement<SendMailsResponse> createSendMailsResponse(SendMailsResponse value) {
        return new JAXBElement<SendMailsResponse>(_SendMailsResponse_QNAME, SendMailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "sendMails")
    public JAXBElement<SendMails> createSendMails(SendMails value) {
        return new JAXBElement<SendMails>(_SendMails_QNAME, SendMails.class, null, value);
    }

}

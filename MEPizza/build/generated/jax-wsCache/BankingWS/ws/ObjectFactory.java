
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
    private final static QName _SendMailToAccount_QNAME = new QName("http://ws/", "sendMailToAccount");
    private final static QName _SendMailToAccountResponse_QNAME = new QName("http://ws/", "sendMailToAccountResponse");

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
     * Create an instance of {@link SendMailToAccount }
     * 
     */
    public SendMailToAccount createSendMailToAccount() {
        return new SendMailToAccount();
    }

    /**
     * Create an instance of {@link SendMailToAccountResponse }
     * 
     */
    public SendMailToAccountResponse createSendMailToAccountResponse() {
        return new SendMailToAccountResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMailToAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "sendMailToAccount")
    public JAXBElement<SendMailToAccount> createSendMailToAccount(SendMailToAccount value) {
        return new JAXBElement<SendMailToAccount>(_SendMailToAccount_QNAME, SendMailToAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMailToAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "sendMailToAccountResponse")
    public JAXBElement<SendMailToAccountResponse> createSendMailToAccountResponse(SendMailToAccountResponse value) {
        return new JAXBElement<SendMailToAccountResponse>(_SendMailToAccountResponse_QNAME, SendMailToAccountResponse.class, null, value);
    }

}

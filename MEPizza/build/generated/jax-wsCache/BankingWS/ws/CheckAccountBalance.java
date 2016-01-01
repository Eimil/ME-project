
package ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for checkAccountBalance complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkAccountBalance">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cardNmbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="csv" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkAccountBalance", propOrder = {
    "price",
    "cardNmbr",
    "csv"
})
public class CheckAccountBalance {

    protected int price;
    protected String cardNmbr;
    protected int csv;

    /**
     * Gets the value of the price property.
     * 
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(int value) {
        this.price = value;
    }

    /**
     * Gets the value of the cardNmbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNmbr() {
        return cardNmbr;
    }

    /**
     * Sets the value of the cardNmbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNmbr(String value) {
        this.cardNmbr = value;
    }

    /**
     * Gets the value of the csv property.
     * 
     */
    public int getCsv() {
        return csv;
    }

    /**
     * Sets the value of the csv property.
     * 
     */
    public void setCsv(int value) {
        this.csv = value;
    }

}

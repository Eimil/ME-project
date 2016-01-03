package hibernate;
// Generated 2016-jan-03 20:56:52 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Card generated by hbm2java
 */
@Entity
@Table(name="card"
    ,catalog="mepizza"
)
public class Card  implements java.io.Serializable {


     private String cardNmbr;
     private double balance;
     private int csv;

    public Card() {
    }

    public Card(String cardNmbr, double balance, int csv) {
       this.cardNmbr = cardNmbr;
       this.balance = balance;
       this.csv = csv;
    }
   
     @Id 

    
    @Column(name="cardNmbr", unique=true, nullable=false, length=40)
    public String getCardNmbr() {
        return this.cardNmbr;
    }
    
    public void setCardNmbr(String cardNmbr) {
        this.cardNmbr = cardNmbr;
    }

    
    @Column(name="balance", nullable=false, precision=22, scale=0)
    public double getBalance() {
        return this.balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }

    
    @Column(name="csv", nullable=false)
    public int getCsv() {
        return this.csv;
    }
    
    public void setCsv(int csv) {
        this.csv = csv;
    }




}



package hibernate;
// Generated 2015-dec-29 22:18:04 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Restaurant generated by hbm2java
 */
@Entity
@Table(name="restaurant"
    ,catalog="mepizza"
)
public class Restaurant  implements java.io.Serializable {


     private Integer id;
     private String name;
     private String phone;
     private String address;
     private String zip;
     private Date start;
     private Date close;

    public Restaurant() {
    }

    public Restaurant(String name, String phone, String address, String zip, Date start, Date close) {
       this.name = name;
       this.phone = phone;
       this.address = address;
       this.zip = zip;
       this.start = start;
       this.close = close;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="phone", nullable=false)
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    @Column(name="address", nullable=false)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name="zip", nullable=false, length=6)
    public String getZip() {
        return this.zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="start", nullable=false, length=8)
    public Date getStart() {
        return this.start;
    }
    
    public void setStart(Date start) {
        this.start = start;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="close", nullable=false, length=8)
    public Date getClose() {
        return this.close;
    }
    
    public void setClose(Date close) {
        this.close = close;
    }




}



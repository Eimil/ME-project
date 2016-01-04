package hibernate;
// Generated 2016-jan-04 15:51:31 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name="product"
    ,catalog="mepizza"
)
public class Product  implements java.io.Serializable {


     private Integer id;
     private String name;
     private String description;
     private double price;
     private String picLink;

    public Product() {
    }

    public Product(String name, String description, double price, String picLink) {
       this.name = name;
       this.description = description;
       this.price = price;
       this.picLink = picLink;
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

    
    @Column(name="description", nullable=false, length=65535)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="price", nullable=false, precision=22, scale=0)
    public double getPrice() {
        return this.price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    
    @Column(name="picLink", nullable=false)
    public String getPicLink() {
        return this.picLink;
    }
    
    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }




}



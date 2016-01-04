package hibernate;
// Generated 2016-jan-04 14:44:56 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cart generated by hbm2java
 */
@Entity
@Table(name="cart"
    ,catalog="mepizza"
)
public class Cart  implements java.io.Serializable {


     private Integer id;
     private int userId;
     private int productId;

    public Cart() {
    }

    public Cart(int userId, int productId) {
       this.userId = userId;
       this.productId = productId;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="userId", nullable=false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    @Column(name="productID", nullable=false)
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }




}



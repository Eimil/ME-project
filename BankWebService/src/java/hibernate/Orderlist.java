package hibernate;
// Generated 2016-jan-12 20:02:03 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Orderlist generated by hbm2java
 */
@Entity
@Table(name="orderlist"
    ,catalog="mepizza"
)
public class Orderlist  implements java.io.Serializable {


     private Integer id;
     private int orderId;
     private int productId;

    public Orderlist() {
    }

    public Orderlist(int orderId, int productId) {
       this.orderId = orderId;
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

    
    @Column(name="OrderId", nullable=false)
    public int getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    
    @Column(name="ProductId", nullable=false)
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }




}



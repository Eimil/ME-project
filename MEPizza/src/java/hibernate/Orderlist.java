package hibernate;
// Generated 2015-dec-25 21:47:54 by Hibernate Tools 4.3.1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Orderlist generated by hbm2java
 */
@Entity
@Table(name="orderlist"
    ,catalog="mepizza"
)
public class Orderlist  implements java.io.Serializable {


     private OrderlistId id;

    public Orderlist() {
    }

    public Orderlist(OrderlistId id) {
       this.id = id;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="orderId", column=@Column(name="OrderId", nullable=false) ), 
        @AttributeOverride(name="productId", column=@Column(name="ProductId", nullable=false) ) } )
    public OrderlistId getId() {
        return this.id;
    }
    
    public void setId(OrderlistId id) {
        this.id = id;
    }




}



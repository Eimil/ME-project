package hibernate;
// Generated 2015-dec-25 21:47:54 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OrderlistId generated by hbm2java
 */
@Embeddable
public class OrderlistId  implements java.io.Serializable {


     private int orderId;
     private int productId;

    public OrderlistId() {
    }

    public OrderlistId(int orderId, int productId) {
       this.orderId = orderId;
       this.productId = productId;
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


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof OrderlistId) ) return false;
		 OrderlistId castOther = ( OrderlistId ) other; 
         
		 return (this.getOrderId()==castOther.getOrderId())
 && (this.getProductId()==castOther.getProductId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getOrderId();
         result = 37 * result + this.getProductId();
         return result;
   }   


}



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
 * Order generated by hbm2java
 */
@Entity
@Table(name="order"
    ,catalog="mepizza"
)
public class Order  implements java.io.Serializable {


     private Integer id;
     private Date time;
     private int userId;
     private int storeId;
     private String notes;
     private int restaurant;

    public Order() {
    }

    public Order(Date time, int userId, int storeId, String notes, int restaurant) {
       this.time = time;
       this.userId = userId;
       this.storeId = storeId;
       this.notes = notes;
       this.restaurant = restaurant;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time", nullable=false, length=19)
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }

    
    @Column(name="userId", nullable=false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    @Column(name="storeId", nullable=false)
    public int getStoreId() {
        return this.storeId;
    }
    
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    
    @Column(name="notes", nullable=false, length=65535)
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    
    @Column(name="restaurant", nullable=false)
    public int getRestaurant() {
        return this.restaurant;
    }
    
    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }




}



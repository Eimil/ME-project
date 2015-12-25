package hibernate;
// Generated 2015-dec-25 21:47:54 by Hibernate Tools 4.3.1


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
 * Orderlog generated by hbm2java
 */
@Entity
@Table(name="orderlog"
    ,catalog="mepizza"
)
public class Orderlog  implements java.io.Serializable {


     private Integer id;
     private int orderId;
     private Date time;
     private String type;
     private String note;
     private int userId;

    public Orderlog() {
    }

    public Orderlog(int orderId, Date time, String type, String note, int userId) {
       this.orderId = orderId;
       this.time = time;
       this.type = type;
       this.note = note;
       this.userId = userId;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="orderId", nullable=false)
    public int getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time", nullable=false, length=19)
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }

    
    @Column(name="type", nullable=false)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    @Column(name="note", nullable=false, length=65535)
    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }

    
    @Column(name="userId", nullable=false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }




}



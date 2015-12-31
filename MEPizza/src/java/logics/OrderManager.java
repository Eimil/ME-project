package logics;

import hibernate.Cart;
import hibernate.HibernateUtil;
import hibernate.Order;
import hibernate.Orderlist;
import hibernate.Product;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class OrderManager implements OrderManagerLocal {

    @Override
    public void createOrder(String[] orderValues) {
        //System.out.println("UserID "+orderValues[7]+"Notes "+orderValues[0]+"Store "+orderValues[6]);
       
        Session session = null;
        int orderId = -1;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Order order = new Order();
            order.setUserId(Integer.parseInt(orderValues[7]));
            order.setNotes(orderValues[0]);
            order.setStoreId(Integer.parseInt(orderValues[6]));
            session.save(order);
            session.getTransaction().commit();
            orderId = order.getId();
        } catch (Exception ex) {
            System.out.println("Exception in creating order : " + ex);
        }
        if (session != null) {
            session.clear();
            session.close();
        }
        if (orderId != -1) {
            addProductsToOrder(Integer.parseInt(orderValues[7]), orderId);
        }
    }

    private void addProductsToOrder(int userId, int orderId) {
        System.out.println("addProductsToOrder() is called");
        
        Session session = null;
        List<Cart> theCart = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            theCart = (List<Cart>) session.createQuery("select cart from Cart cart where cart.userId = :userId").setParameter("userId", userId).list();
            session.getTransaction().commit();
            int total = 0;

            for (int i = 0; i < theCart.size(); i++) {
                Cart pr = theCart.get(i);
                
                
                //Lägg till i ordern
                
                System.out.println("OderID"+orderId+"Prodcut"+pr.getProductId());
                session.beginTransaction();
                Orderlist orderlist = new Orderlist();
                orderlist.setOrderId(orderId);
                orderlist.setProductId(pr.getProductId());
                session.save(orderlist);
                session.getTransaction().commit();

                //Ta bort
            }
  

            session.close();

        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }
   
    }

}

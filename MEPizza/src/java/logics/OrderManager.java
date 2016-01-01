package logics;

import hibernate.Cart;
import hibernate.HibernateUtil;
import hibernate.Order;
import hibernate.Orderlist;
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
        Session session = null;
        int orderId = -1;
        System.out.println("Priset borde vara : " +  orderValues[7]);
        // Kolla om kunden har pengar
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Order order = new Order();
            order.setUserId(Integer.parseInt(orderValues[8]));
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
            addProductsToOrder(Integer.parseInt(orderValues[8]), orderId);
        }
    }

    private void addProductsToOrder(int userId, int orderId) {

        Session session = null;
        List<Cart> theCart = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            theCart = (List<Cart>) session.createQuery("select cart from Cart cart where cart.userId = :userId").setParameter("userId", userId).list();
            session.getTransaction().commit();

            for (int i = 0; i < theCart.size(); i++) {
                Cart cart = theCart.get(i);
                System.out.println("OderID" + orderId + "Prodcut" + cart.getProductId());
                session.beginTransaction();
                Orderlist orderlist = new Orderlist();
                orderlist.setOrderId(orderId);
                orderlist.setProductId(cart.getProductId());
                session.save(orderlist);
                session.getTransaction().commit();

                //String hql = "delete from Cart where id= :id";
                session.createQuery("delete from Cart where id= :id").setParameter("id", cart.getId()).executeUpdate();
                // Maila kunden om allt gick som det skulle
            }
            session.close();
        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }

    }

}

package logics;

import hibernate.HibernateUtil;
import hibernate.Order;
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
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Order order = new Order();
            order.setUserId(Integer.parseInt(orderValues[0]));
            order.setNotes(orderValues[0]);
            order.setStoreId(Integer.parseInt(orderValues[2]));
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
            addProductsToOrder(orderId, orderId);
        }
    }

    private void addProductsToOrder(int userId, int orderId) {
        System.out.println("IN ADDPRODUCTS TO ORDER");
    }

}

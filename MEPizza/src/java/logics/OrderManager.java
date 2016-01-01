package logics;

import hibernate.Cart;
import hibernate.HibernateUtil;
import hibernate.Order;
import hibernate.Orderlist;
import hibernate.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import org.hibernate.Session;
import ws.BankingWS_Service;
import ws.BankingWS;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class OrderManager implements OrderManagerLocal {

    @WebServiceRef(wsdlLocation = "http://localhost:8080/BankWebService/BankingWS?wsdl")
    private BankingWS_Service service;

    @Override
    public String createOrder(String[] orderValues) {
        Session session = null;
        int orderId = -1;
        if (callWebServiceAboutBank(Integer.parseInt(orderValues[7]), orderValues[5], Integer.parseInt(orderValues[2])).equals("Transaction complete")) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                Order order = new Order();
                order.setUserId(Integer.parseInt(orderValues[8]));
                order.setNotes(orderValues[0]);
                order.setStoreId(Integer.parseInt(orderValues[6]));
                order.setPrice(Integer.parseInt(orderValues[7]));
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
                if(addProductsToOrder(Integer.parseInt(orderValues[8]), orderId).equalsIgnoreCase("Mail was sent"))
                return "CREATED & MAILED";
            } else if (addProductsToOrder(Integer.parseInt(orderValues[8]), orderId).equalsIgnoreCase("Mail was not sent")) {
                return "Couldn't send mail to user";
            } else {
                return "Couldn't add products to order";
            }
        }
        return "Could not make order, insufficent funds";
    }

    private String callWebServiceAboutBank(int price, String cardNumber, int csv) {
        try {
            BankingWS port = service.getBankingWSPort();
            return port.checkAccountBalance(price, cardNumber, csv);
        } catch (Exception ex) {
            System.out.println("Couldn't contact bank " + ex);
        }
        return "Couldn't call web service about bank";
    }

    private String addProductsToOrder(int userId, int orderId) {

        Session session = null;
        List<Cart> theCart = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            theCart = (List<Cart>) session.createQuery("select cart from Cart cart where cart.userId = :userId").setParameter("userId", userId).list();
            session.getTransaction().commit();

            for (int i = 0; i < theCart.size(); i++) {
                Cart cart = theCart.get(i);
                session.beginTransaction();
                Orderlist orderlist = new Orderlist();
                orderlist.setOrderId(orderId);
                orderlist.setProductId(cart.getProductId());
                session.save(orderlist);
                session.getTransaction().commit();
                session.createQuery("delete from Cart where id= :id").setParameter("id", cart.getId()).executeUpdate();
            }
            session.close();
            if (callWebServiceAboutMail(userId, "YOU ORDERED PIZZA", String.valueOf(orderId))) {
                return "Mail was sent";
            } else {
                return "Mail was not sent";
            }
        } catch (Exception ex) {
            System.out.println("Exception in adding products to order : " + ex);
        }
        return "Error in adding products to order";
    }

    private boolean callWebServiceAboutMail(int userId, String orderInfo, String orderNumber) {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.id = :id")
                    .setParameter("id", userId)
                    .uniqueResult();
            session.getTransaction().commit();
            session.close();
            if (user.getFullName() != null) {
                BankingWS port = service.getBankingWSPort();
                return port.sendMailToAccount(user.getEmail(), orderInfo, orderNumber);
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error in callWebServiceAbout mail : " + ex);
        }
        return true;
    }

}

package logics;

/*
* The Stateless Session Bean which performs the logics behind managing orders.
 */
import hibernate.Cart;
import hibernate.HibernateUtil;
import hibernate.Order;
import hibernate.Orderlist;
import hibernate.Product;
import hibernate.Restaurant;
import hibernate.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import org.hibernate.Session;
import ws.BankingWS;
import ws.BankingWS_Service;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class OrderManager implements OrderManagerLocal {

    @WebServiceRef(wsdlLocation = "http://localhost:8080/BankWebService/BankingWS?wsdl")
    private BankingWS_Service service;

    
    /*
    * Method used to create an order, calls web service to check account balance.
     */
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
                if (addProductsToOrder(Integer.parseInt(orderValues[8]), Integer.parseInt(orderValues[6]), orderId).equalsIgnoreCase("Mail was sent")) {
                    return "CREATED & MAILED";
                } else {
                    return "Couldn't send mail to user";
                }
            }
            return "Couldn't add products to order";
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

    /*
    * Method used to add products to an order
     */
    private String addProductsToOrder(int userId, int storeId, int orderId) {
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
            if (callWebServiceAboutMail(userId, storeId, String.valueOf(orderId))) {
                return "Mail was sent";
            } else {
                return "Mail was not sent";
            }
        } catch (Exception ex) {
            System.out.println("Exception in adding products to order : " + ex);
        }
        return "Error in adding products to order";
    }

    /*
    * Method used to call web service to send a mail to a specific address.
     */
    private boolean callWebServiceAboutMail(int userId, int storeId, String orderNumber) {
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
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                String restaurantEmail = null;
                Restaurant restaurant = (Restaurant) session.createQuery("select restaurant from Restaurant restaurant where restaurant.id = :id")
                        .setParameter("id", storeId)
                        .uniqueResult();
                session.getTransaction().commit();
                session.close();
                if (restaurant.getEmail() != null) {
                    restaurantEmail = restaurant.getEmail();
                }      
                List<String> userInfo = new ArrayList<String>();
                userInfo.add("Namn: " + user.getFullName() + "\n");
                userInfo.add("Adress: " + user.getAddress() + "\n");
                userInfo.add("Postnummer: " + user.getZipCode() + "\n");
                userInfo.add("Telefonnummer: " + user.getPhone() + "\n");
                userInfo.add(user.getEmail());
                String orderInfo = getContentForMail(orderNumber);
                BankingWS port = service.getBankingWSPort();
                return port.sendMails(userInfo, orderInfo, orderNumber, restaurantEmail);
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error in callWebServiceAbout mail : " + ex);
        }
        return true;
    }

    /*
    * Method used to get the content for the mail to the user.
     */
    private String getContentForMail(String orderNumber) {
        Session session = null;
        String mailContent = "\n";
        List<Orderlist> orderList = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Order order = (Order) session.createQuery("select order from Order order where order.id = :id")
                    .setParameter("id", Integer.parseInt(orderNumber))
                    .uniqueResult();
            session.getTransaction().commit();
            session.close();
            if (order.getId() != null) {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                orderList = (List<Orderlist>) session.createQuery("select orderlist from Orderlist orderlist where orderlist.orderId = :orderId").setParameter("orderId", Integer.parseInt(orderNumber)).list();
                session.getTransaction().commit();
                session.close();
                String summarizedProducts = "Produkter \n";

                for (int i = 0; i < orderList.size(); i++) {
                    session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    int prodId = orderList.get(i).getProductId();
                    Product product = (Product) session.createQuery("select product from Product product where product.id = :productId")
                            .setParameter("productId", prodId)
                            .uniqueResult();
                    session.getTransaction().commit();
                    summarizedProducts += "\nNamn " + product.getName() + "\n" + "Pris : " + product.getPrice() + "\n";
                    session.close();
                }
                mailContent += summarizedProducts + "\n";
                mailContent += "Totalkostnad :" + String.valueOf(order.getPrice()) + "\n";
                mailContent += "Noteringar : " + order.getNotes() + "\n";
                mailContent += "Tidpunkt : " + String.valueOf(order.getTime()) + "\n";
            }
        } catch (Exception ex) {
            System.out.println("Couldn't find info for mail content " + ex);
        }
        return mailContent;
    }

    private boolean sendMails(java.util.List<java.lang.String> userInfo, java.lang.String orderInfo, java.lang.String orderNumber, java.lang.String restaurantEmail) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        ws.BankingWS port = service.getBankingWSPort();
        return port.sendMails(userInfo, orderInfo, orderNumber, restaurantEmail);
    }

}

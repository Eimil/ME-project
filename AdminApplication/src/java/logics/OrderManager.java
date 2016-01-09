package logics;

/*
* The Stateless Session Bean which performs the logics behind handling orders.
 */
import hibernate.HibernateUtil;
import hibernate.Order;
import hibernate.Orderlist;
import hibernate.Product;
import hibernate.Restaurant;
import hibernate.User;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.hibernate.Session;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class OrderManager implements OrderManagerLocal {

    /*
    *   The reference to the EJB used to translate orderstatus/storeId
     */
    @EJB
    private UtilsLocal utils;

    /*
    *   Method which is called to get purchases(orders) of an restaurant.
     */
    @Override
    public String getPurchasesAsHtmlRows(int storeId) {
        Session session = null;
        List<Order> products = null;
        String returner = "";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            products = (List<Order>) session.createQuery("from Order order where order.storeId = :storeId ORDER BY id DESC").setParameter("storeId", storeId).list();
            session.getTransaction().commit();

            for (int i = 0; i < products.size(); i++) {
                Order orderRow = products.get(i);
                session.beginTransaction();
                Restaurant restaurant = (Restaurant) session.createQuery("select restaurant from Restaurant restaurant where restaurant.id = :id")
                        .setParameter("id", orderRow.getStoreId())
                        .uniqueResult();
                session.getTransaction().commit();

                String notes = "";
                if (orderRow.getNotes().length() >= 202) {
                    notes = orderRow.getNotes().substring(0, 200);
                } else {
                    notes = orderRow.getNotes();
                }

                String confirm = "";
                if (orderRow.getStatus().equals("new")) {
                    confirm = "<input type=\"submit\" name=\"finishButton\" value=\"Avsluta order\">";
                }
                returner += " "
                        + "<form action=\"OrderServlet\" method=\"post\">"
                        + "<input type=\"hidden\" name='id' value='" + orderRow.getId() + "'"
                        + "<thead>\n"
                        + "                <th>OrderId</th>\n"
                        + "                <th>Datum</th>\n"
                        + "                <th>Pizzeria</th>\n"
                        + "                <th>Status</th>\n"
                        + "                <th>Anteckningar</th>\n"
                        + "    \n"
                        + "            </thead>\n"
                        + "            <tr>\n"
                        + "                <td>" + orderRow.getId() + "</td>\n"
                        + "                <td>" + orderRow.getTime() + "</td>\n"
                        + "                <td>" + restaurant.getName() + "</td>\n"
                        + "                <td>" + utils.translateStatus(orderRow.getStatus(), "swe") + "</td>\n"
                        + "                <td class=\"notes\" title='" + orderRow.getNotes() + "'>" + notes + "</td>\n"
                        + "                \n"
                        + "            \n"
                        + "            </tr>\n"
                        + "            \n"
                        + "              <tr class='header'>\n"
                        + "                  <td colspan=\"5\">Produkter</td>\n"
                        + "            </tr>\n"
                        + getProductsInOrderAsHtmlRow(orderRow.getId())
                        + "            <tr >\n"
                        + "                  <td colspan=\"2\" class=\"total\" >Totalt " + orderRow.getPrice() + "kr</td>\n"
                        + "                  <td colspan=\"3\">" + confirm + "<input type=\"submit\" name=\"removeButton\" value=\"Makulera order\"></td>\n"
                        + "            </tr>\n"
                        + "              <tr  class='emptyRow'>\n"
                        + "                  <td colspan=\"5\"></td>\n"
                        + "            </tr>\n"
                        + "</form>";

            }
        } catch (Exception ex) {
            System.out.println("Exception in finding orderRow : " + ex);
        }

        return returner;

    }

    /*
    *   Method which is called to get the products of an order.
     */
    private String getProductsInOrderAsHtmlRow(int OrderId) {
        System.out.println("OrderID:" + OrderId);
        Session session = null;
        List<Orderlist> orderlist = null;
        String returner = "";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            orderlist = (List<Orderlist>) session.createQuery("from Orderlist where OrderId='" + OrderId + "'").list();
            session.getTransaction().commit();

            for (int i = 0; i < orderlist.size(); i++) {
                Orderlist orderRow = orderlist.get(i);

                session.beginTransaction();
                Product Product = (Product) session.createQuery("select product from Product product where product.id = :id")
                        .setParameter("id", orderRow.getProductId())
                        .uniqueResult();
                session.getTransaction().commit();
                returner += "             <tr>\n"
                        + "                <td>" + Product.getName() + "</td>\n"
                        + "                <td>" + Product.getDescription() + "</td>\n"
                        + "                <td></td>\n"
                        + "                <td>" + Product.getPrice() + "</td>\n"
                        + "                <td colspan='3'></td>\n"
                        + "            </tr>\n";
            }

        } catch (Exception ex) {
            System.out.println("Exception in getProductsInOrderAsHtmlRow : " + ex);
        }
        return returner;
    }

    /*
    *   Method which is called to set the status of an order.
     */
    @Override
    public void setStatus(int id, String status) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Order order = (Order) session.createQuery("select order from Order order where order.id = :id")
                    .setParameter("id", id)
                    .uniqueResult();
            session.getTransaction().commit();
            session.beginTransaction();
            order.setStatus(status);
            session.save(order);
            session.getTransaction().commit();
            session.close();
            sendMail(order.getUserId(), utils.translateStatus(status, "swe"), order.getId());
        } catch (Exception ex) {
            System.out.println("Couldn't modify order " + ex);
        }
    }

    private void sendMail(int userId, String status, int orderNumber) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.id = :id")
                    .setParameter("id", userId)
                    .uniqueResult();
            session.getTransaction().commit();
            session.close();
            if (user.getEmail() != null) {
                Properties props = System.getProperties();
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, null);
                MimeMessage message = new MimeMessage(mailSession);
                Multipart multiPart = new MimeMultipart("alternative");
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
                message.setSubject("Ändrad orderstatus!");
                String content = "Din beställning med ordernummer " + orderNumber + " är nu ändrad till " + status + ".";
                MimeBodyPart body = new MimeBodyPart();
                body.setText(content, "utf-8");
                multiPart.addBodyPart(body);
                message.setContent(multiPart);
                Transport transport = mailSession.getTransport("smtp");
                transport.connect("smtp.gmail.com", "mepizzacontact@gmail.com", "TrialAndError13");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            }
        } catch (Exception ex) {
            System.out.println("Couldn't send mail to user");
        }
    }
}

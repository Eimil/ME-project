package logics;

/*
* The Stateless Session Bean which performs the logics behind showing previous purchases.
 */

import hibernate.HibernateUtil;
import hibernate.Order;
import hibernate.Orderlist;
import hibernate.Product;

import hibernate.Restaurant;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author Magnus Kanfjäll
 */
@Stateless
public class PurchaseHistoryManager implements PurchaseHistoryManagerLocal {

    @Override
    public String getPurchasesAsHtmlRows(int userId) {
        Session session = null;
        List<Order> products = null;
        String returner = "";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            products = (List<Order>) session.createQuery("from Order order where order.userId = :userId ORDER BY id DESC").setParameter("userId", userId).list();
            session.getTransaction().commit();

            for (int i = 0; i < products.size(); i++) {
                Order pr = products.get(i);
                session.beginTransaction();
                Restaurant restaurant = (Restaurant) session.createQuery("select restaurant from Restaurant restaurant where restaurant.id = :id")
                        .setParameter("id", pr.getStoreId())
                        .uniqueResult();
                session.getTransaction().commit();

                returner += " <thead>\n"
                        + "                <th>OrderId</th>\n"
                        + "                <th>Datum</th>\n"
                        + "                <th>Pizzeria</th>\n"
                        + "                <th>Anteckningar</th>\n"
                        + "    \n"
                        + "            </thead>\n"
                        + "            <tr>\n"
                        + "                <td>" + pr.getId() + "</td>\n"
                        + "                <td>" + pr.getTime() + "</td>\n"
                        + "                <td>" + restaurant.getName() + "</td>\n"
                        + "                <td class=\"notes\">" + pr.getNotes() + "</td>\n"
                        + "                \n"
                        + "            \n"
                        + "            </tr>\n"
                        + "            \n"
                        + "              <tr class='header'>\n"
                        + "                  <td colspan=\"5\">Produkter</td>\n"
                        + "            </tr>\n"
                        + getProductsInOrderAsHtmlRow(pr.getId())
                        + "            <tr class=\"total\">\n"
                        + "                  <td colspan=\"5\">Totalt " + pr.getPrice() + "kr</td>\n"
                        + "            </tr>\n"
                        + "              <tr  class='emptyRow'>\n"
                        + "                  <td colspan=\"5\"></td>\n"
                        + "            </tr>\n";

            }
        } catch (Exception ex) {
            System.out.println("Exception in finding orderRow : " + ex);
        }

        return returner;

    }

    private String getProductsInOrderAsHtmlRow(int OrderId) {
        System.out.println("OrderIDD:" + OrderId);
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
            System.out.println("Exception in finding account : " + ex);
        }
        return returner;
    }
}

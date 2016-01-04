package logics;

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
 * @author Emil Ejder
 */
@Stateless
public class OrderManager implements OrderManagerLocal {

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

                String notes="";
                if(orderRow.getNotes().length()>=202){
                    notes=orderRow.getNotes().substring(0, 200);
                }else{
                     notes=orderRow.getNotes();
                }
                returner += " <thead>\n"
                        + "                <th>OrderId</th>\n"
                        + "                <th>Datum</th>\n"
                        + "                <th>Pizzeria</th>\n"
                        + "                <th>Anteckningar</th>\n"
                        + "    \n"
                        + "            </thead>\n"
                        + "            <tr>\n"
                        + "                <td>" + orderRow.getId() + "</td>\n"
                        + "                <td>" + orderRow.getTime() + "</td>\n"
                        + "                <td>" + restaurant.getName() + "</td>\n"
                        + "                <td class=\"notes\" title='"+orderRow.getNotes()+"'>" +notes + "</td>\n"
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
                        + "                  <td colspan=\"3\"><strong><a href='#'>Bekräfta order</a></strong> / <strong><a href='#'>Markulera order</a></strong></td>\n"
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
            System.out.println("Exception in getProductsInOrderAsHtmlRow : " + ex);
        }
        return returner;
    }
}

package logics;

/*
* The Stateless Session Bean which performs the logics behind listing products.
 */
import hibernate.HibernateUtil;
import hibernate.Product;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author Magnus Kanfjäll
 */
@Stateless
public class ProductLister implements ProductListerLocal {

    /*
    *   Method which is called to get the avaliable products.
     */
    @Override
    public String getProductsAsHtmlRows() {
        Session session = null;
        List<Product> products = null;
        String returner = "";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            products = (List<Product>) session.createQuery("from Product where active = 1").list();
            session.getTransaction().commit();
            session.close(); // kanske skall kommenteras bort
        } catch (Exception ex) {
            System.out.println("Exception in listing products : " + ex);
        }

        for (int i = 0; i < products.size(); i++) {
            Product pr = products.get(i);
            returner += "<form action=\"StoreServlet\" method=\"post\">\n"
                    + " \n"
                    + "  <input type=\"hidden\" name=\"id\" value=\""+pr.getId()+"\">";
            returner += "<tr>";     
            returner += "<td>" + pr.getName() + "</td>";
            returner += "<td>" + pr.getDescription() + "</td>";
            returner += "<td><img src=\"" + pr.getPicLink() + "\" height=\"42\" width=\"42\"></td>";
            returner += "<td>" + pr.getPrice() + "</td>";
            returner += "<td><input type=\"submit\" value=\"Lägg till kundvagn\"></td>";

            returner += "</tr></form>";
        }
        return returner;
    }
}

package logics;

/*
* The Stateless Session Bean which performs the logics behind listing products in the cart.
 */

import hibernate.HibernateUtil;
import hibernate.Cart;
import hibernate.Product;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author Magnus Kanfjäll
 */
@Stateless
public class CartLister implements CartListerLocal {

    /*
    *   Method which is called to list items in the shopping cart.
     */
    @Override
    public String cartContensAsHtmlRow(int userId) {

        Session session = null;
        List<Cart> theCart = null;
        String returner = "";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            theCart = (List<Cart>) session.createQuery("select cart from Cart cart where cart.userId = :userId").setParameter("userId", userId).list();
            session.getTransaction().commit();
            int total = 0;

            for (int i = 0; i < theCart.size(); i++) {
                Cart pr = theCart.get(i);

                session.beginTransaction();
                Product Product = (Product) session.createQuery("select product from Product product where product.id = :id")
                        .setParameter("id", pr.getProductId())
                        .uniqueResult();
                session.getTransaction().commit();

                returner += "<tr>";

                returner += "<td>" + Product.getName() + "</td>";
                returner += "<td>1st</td>";
                returner += "<td>" + Product.getPrice() + "kr</td>";

                returner += "</tr>";

                total += Product.getPrice();

            }
            returner += "<tr class='total'><td colspan='2'>Total:</td><td>" + total + "kr</td><tr>";

            session.close();

        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }
        return returner;
    }
}

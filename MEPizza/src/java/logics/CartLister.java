package logics;

/*
* The Stateless Session Bean which performs the logics behind listing products in the cart.
 */
import hibernate.HibernateUtil;
import hibernate.Cart;
import hibernate.Product;
import hibernate.User;
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
    public String[] cartContensAsHtmlRow(int userId) {

        Session session = null;
        List<Cart> theCart = null;
        String returner = "";
        int total = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            theCart = (List<Cart>) session.createQuery("select cart from Cart cart where cart.userId = :userId").setParameter("userId", userId).list();
            session.getTransaction().commit();

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
        String priceAsString = String.valueOf(total);
        String[] resultArray = new String[2];
        resultArray[0] = returner;
        resultArray[1] = priceAsString;
        return resultArray;
    }

    /*
    * Method used to generate info about the current user for the checkout.
     */
    @Override
    public String getUserInfo(int userId) {
        try {
            String summarizedUserInfo = "";
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.id = :id")
                    .setParameter("id", userId)
                    .uniqueResult();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            summarizedUserInfo = " <tr>\n"
                    + " <td>Kundnamn</td>\n"
                    + " <td>" + user.getFullName() + "</td>\n"
                    + " <td>Email</td>\n"
                    + " <td>" + user.getEmail() + "</td>\n"
                    + " </tr>\n"
                    + " <tr>\n"
                    + " <td>Adress</td>\n"
                    + " <td>" + user.getAddress() + "</td>\n"
                    + " <td>Postnummer</td>\n"
                    + " <td>" + user.getZipCode() + "</td>\n"
                    + " </tr>\n"
                    + " <tr>\n"
                    + " <td>Telefonnummer</td>\n"
                    + " <td>" + user.getPhone() + "</td>\n"
                    + " <td></td>\n"
                    + " <td></td>\n"
                    + " </tr>";
            return summarizedUserInfo;
        } catch (Exception ex) {
            System.out.println("Couldn't load user info" + ex);
        }
        String[] error = new String[]{"BAD"};
        return "error in loading user info for checkout";
    }
}

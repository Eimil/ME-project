package logics;

/*
* The Stateless Session Bean which performs the logics behind adding items to cart.
* Is also used to list the avaliable restaurants.
 */
import hibernate.HibernateUtil;
import hibernate.Cart;
import hibernate.Restaurant;
import java.util.List;

import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author Magnus Kanfjäll
 */
@Stateless
public class CartHandler implements CartHandlerLocal {

    /*
    * Method used to add content to cart
     */
    @Override
    public void addToCart(int productId, int userId) {
        System.out.println("Produkt:" + productId + "User:" + userId);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Cart cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            session.save(cart);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Exception in adding product to cart : " + ex);
        }
        if (session != null) {
            session.clear();
            session.close();
        }
    }

    /*
    * Method used to remove content from cart
     */
    @Override
    public void removeFromCart(int rowId, int userId) {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("delete from Cart where id= :id AND userId= :userId").setParameter("id", rowId).setParameter("userId", userId).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Exception in removing from cart : " + ex);
        }
        if (session != null) {
            session.clear();
            session.close();
        }

    }

    /*
    * Method used to generate content for the dropdown containing restaurants
     */
    @Override
    public String resturantDropdownHtml() {
        Session session = null;
        List<Restaurant> restaurant = null;
        String returner = "";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            restaurant = (List<Restaurant>) session.createQuery("from Restaurant").list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            System.out.println("Exception in removing from cart : " + ex);
        }

        for (int i = 0; i < restaurant.size(); i++) {
            Restaurant pr = restaurant.get(i);
            returner += " <option value='" + pr.getId() + "'>" + pr.getName() + "</option>";
        }

        return returner;

    }

}

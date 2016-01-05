package logics;

/*
* The Stateless Session Bean which performs the logics behind adding/removing products.
 */
import hibernate.HibernateUtil;
import hibernate.Product;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class ProductHandler implements ProductHandlerLocal {

    /*
    *   Method which is called to add a product to the menu.
     */
    @Override
    public boolean addProduct(String[] productInfo) {
        Session session = null;
        boolean success = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Product product = new Product();
            product.setName(productInfo[0]);
            product.setDescription(productInfo[1]);
            product.setPicLink(productInfo[2]);
            product.setPrice(Double.parseDouble(productInfo[3]));
            session.save(product);
            session.getTransaction().commit();
            success = true;
        } catch (Exception ex) {
            System.out.println("Exception in adding product to the menu : " + ex);
        }
        if (session != null) {
            session.clear();
            session.close();
        }
        return success;
    }

    /*
    *   Method which is called remove a product from the menu.
     */
    @Override
    public boolean removeProduct(int productId) {
        Session session = null;
        boolean success = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("delete from Product where id= :id").setParameter("id", productId).executeUpdate();
            session.getTransaction().commit();
            success = true;
        } catch (Exception ex) {
            System.out.println("Exception in removing product to the menu : " + ex);
        }
        if (session != null) {
            session.clear();
            session.close();
        }
        return success;
    }
}

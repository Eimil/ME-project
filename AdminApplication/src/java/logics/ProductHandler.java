package logics;

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

    @Override
    public boolean addProduct(String[] productInfo) {
        Session session = null;
        boolean success = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Product product = new Product();
            product.setName("NAME");
            product.setDescription("DESCRIPTION");
            product.setPicLink("PICTURELINK");
            product.setPrice(1);
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

    @Override
    public boolean removeProduct(int productId) {
        Session session = null;
        boolean success = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("delete from Product where id= :id").setParameter("id", productId).executeUpdate();
            session.getTransaction().commit();
            session.close();
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

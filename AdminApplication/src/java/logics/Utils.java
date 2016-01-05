package logics;

/*
* The Stateless Session Bean which handles the translation of an orders status
* and the translation between userId and storeId.
 */
import hibernate.HibernateUtil;
import hibernate.User;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author Magnus Kanfjäll
 */
@Stateless
public class Utils implements UtilsLocal {

    /*
    *   Method which is called to get the storeId linked to the userId.
     */
    @Override
    public int getStoreIdByUserId(int userId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.id = :id").setParameter("id", userId).uniqueResult();
            session.getTransaction().commit();
            if (user.getFullName() != null) {
                return user.getStoreId();
            }
            session.close(); // kanske skall kommenteras bort
        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }

        return 0;
    }

    /*
    *   Method which is called to translate the order status.
     */
    @Override
    public String translateStatus(String key, String lang) {

        switch (lang) {
            case "swe":
                switch (key) {
                    case "new":
                        return "Ny order";
                    case "finished":
                        return "Avslutad";
                    case "erased":
                        return "Makulerad";
                    default:
                        break;
                }
            default:
                return key;
        }

    }
}

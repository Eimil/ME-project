package logics;

/*
* The Stateless Session Bean which performs the logics behind logging an account in.
 */

import hibernate.HibernateUtil;
import hibernate.User;
import java.security.MessageDigest;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;
import org.hibernate.Session;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class LoginChecker implements LoginCheckerLocal {

    /*
    *   Method which is called to hash a selected String.
     */
    @Override
    public String hashString(String param) {
        byte[] stringDigested = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(param.getBytes("UTF-8"));
            stringDigested = md.digest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return DatatypeConverter.printHexBinary(stringDigested);
    }

    /*
    *   Method which is called to check if the credentials is valid.
     */
    @Override
    public String checkIfValid(String username, String password) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.userName = :username and user.password = :password and user.role = :role")
                    .setParameter("username", username).setParameter("password", password).setParameter("role", "admin")
                    .uniqueResult();
            session.getTransaction().commit();
            if (user.getFullName() != null) {
                return user.getId().toString();
            }
            session.close(); // kanske skall kommenteras bort
        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }
        return "BAD";
    }

}

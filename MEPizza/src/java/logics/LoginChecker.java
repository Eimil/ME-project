package logics;

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

    @Override
    public String hashString(String param) {
        byte[] stringDigested = null;
        try {
            MessageDigest md=  MessageDigest.getInstance("SHA-256");
            md.update(param.getBytes("UTF-8"));
            stringDigested = md.digest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return DatatypeConverter.printHexBinary(stringDigested);
    }

    @Override
    public String checkIfValid(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.userName = :username and user.password = :password")
                    .setParameter("username", username).setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            System.out.println("THE USERS FULL NAME IS " + user.getFullName());
            if (user.getFullName() != null) {
                System.out.println("Username is : " + user.getUserName());
                System.out.println("Password is : " + user.getPassword());
                return user.getId().toString();
            }
            session.close(); // kanske skall kommenteras bort
        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }
        return "BAD";
    }

}

package logics;

/*
* The Stateless Session Bean which performs the logics behind creating an account.
 */
import java.security.MessageDigest;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;
import hibernate.HibernateUtil;
import hibernate.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class AccountCreator implements AccountCreatorLocal {

    /*
    *   Method which is called to hash the selected String.
     */
    @Override
    public String hashString(String userParam) {
        byte[] usernameDigested = null;
        try {
            MessageDigest paramMD = MessageDigest.getInstance("SHA-256");
            paramMD.update(userParam.getBytes("UTF-8"));
            usernameDigested = paramMD.digest();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return DatatypeConverter.printHexBinary(usernameDigested);
    }

    /*
    *   Method which is called to create an account (User).
     */
    @Override
    public boolean createAccount(String[] accountParams) {
        Session session = null;
        boolean success = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = new User();
            user.setUserName(accountParams[0]);
            user.setFullName(accountParams[1]);
            user.setAddress(accountParams[2]);
            user.setZipCode(accountParams[3]);
            user.setPassword(accountParams[4]);
            user.setEmail(accountParams[5]);
            user.setPhone(accountParams[6]);
            user.setActive(true);
            user.setRole("user");
            session.save(user);
            session.getTransaction().commit();
            success = true;
        } catch (Exception ex) {
            System.out.println("Exception in creating account : " + ex);
        }
        if (session != null) {
            session.clear();
            session.close();
        }
        return success;
    }

    /*
    *   Method which is called to check if the credentials is avaliable.
     */
    @Override
    public String checkIfCredsAvaliable(String username, String email) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            List<String> emailsTaken = new ArrayList<>();
            List<String> usernamesTaken = new ArrayList<>();
            List<User> users = (List<User>) session.createQuery("from User").list();
            for (Iterator i = users.iterator(); i.hasNext();) {
                User user = (User) i.next();
                emailsTaken.add(user.getEmail());
                usernamesTaken.add(user.getUserName());
            }
            session.getTransaction().commit();
            if (!usernamesTaken.contains(username)) {
                if (!emailsTaken.contains(email)) {
                    session.close();
                    return "OK";
                } else {
                    return "Email taken";
                }
            } else {
                return "Username taken";
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong with checking if credentials is avaliable : " + ex);
        }
        return "error";
    }
}

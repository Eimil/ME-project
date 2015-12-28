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
public class AccountInfoChanger implements AccountInfoChangerLocal {

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

    @Override
    public String changeUserInfo(String[] newInfo) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.email = :email and user.active='1'")
                    .setParameter("email", newInfo[0])
                    .uniqueResult();
            session.getTransaction().commit();
            session.beginTransaction();
            // set nya värden på usern
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return "OK";
        } catch (Exception ex) {
            System.out.println("Couldn't modify account");
        }
        return "BAD";
    }

    @Override
    public String[] loadUserInfo(String previousInfo) {
        String[] userInfo = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.email = :email")
                    .setParameter("email", previousInfo)
                    .uniqueResult();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return userInfo;
        } catch (Exception ex) {
            System.out.println("Couldn't load user info");
        }
        String[] error = new String[]{"BAD"};
        return error;
    }

}

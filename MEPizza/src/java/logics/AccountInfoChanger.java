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
    public String changeUserInfo(String[] newInfo, String userId) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.id = :id")
                    .setParameter("id", Integer.parseInt(userId))
                    .uniqueResult();
            session.getTransaction().commit();
            session.beginTransaction();
            if (newInfo[0] != null && !newInfo[0].equalsIgnoreCase("")) {
                user.setFullName(newInfo[0]);
                System.out.println("The changed name is : " + newInfo[0]);
            }
            if (newInfo[1] != null && !newInfo[1].equalsIgnoreCase("")) {
                user.setAddress(newInfo[1]);
                System.out.println("The changed address is : " + newInfo[0]);
            }
            if (newInfo[2] != null && !newInfo[2].equalsIgnoreCase("")) {
                user.setZipCode(newInfo[2]);
                System.out.println("The changed zipcode is : " + newInfo[0]);
            }
            if (newInfo[3] != null && !newInfo[3].equalsIgnoreCase("")) {
                user.setPassword(hashString(newInfo[3]));
                System.out.println("The changed password is : " + newInfo[0]);
            }
            if (newInfo[4] != null && !newInfo[4].equalsIgnoreCase("")) {
                user.setEmail(newInfo[4]);
                System.out.println("The changed email is : " + newInfo[0]);
            }
            if (newInfo[5] != null && !newInfo[5].equalsIgnoreCase("")) {
                user.setPhone(newInfo[5]);
                System.out.println("The changed phone is : " + newInfo[0]);
            }
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return "GOOD";
        } catch (Exception ex) {
            System.out.println("Couldn't modify account " + ex);
        }
        return "BAD";
    }

    @Override
    public String[] loadUserInfo(String userId) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.id = :id")
                    .setParameter("id", Integer.parseInt(userId))
                    .uniqueResult();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            String[] currentUserInfo = new String[5];
            currentUserInfo[0] = user.getFullName();
            currentUserInfo[1] = user.getAddress();
            currentUserInfo[2] = user.getZipCode();
            currentUserInfo[3] = user.getEmail();
            currentUserInfo[4] = user.getPhone();
            return currentUserInfo;
        } catch (Exception ex) {
            System.out.println("Couldn't load user info" + ex);
        }
        String[] error = new String[]{"BAD"};
        return error;
    }

    @Override
    public boolean checkPassword(String hashedPassword) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.createQuery("select user from User user where user.password = :password")
                    .setParameter("password", hashedPassword)
                    .uniqueResult();
            session.getTransaction().commit();
            session.close();
            return user.getPassword().equalsIgnoreCase(hashedPassword);
        } catch (Exception ex) {
            System.out.println("Couldn't check password " + ex);
        }
        return false;
    }

}

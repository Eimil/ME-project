/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

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
}

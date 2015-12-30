/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

import hibernate.Cart;
import hibernate.HibernateUtil;
import hibernate.Product;
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
    
    
@Override
public void addToCart(int productId,int userId){
    System.out.println("Produkt:"+productId+"User:"+userId);
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
            System.out.println("Exception in adding prdukt : " + ex);
        }
        if (session != null) {
            session.clear();
            session.close();
        }
       
    
}
 
@Override
public void removeFromCart(int productId,int userId){
    //Not implmented
    
}

@Override
public String resturantDropdownHtml(){
     Session session = null;
        List<Restaurant> restaurant = null;
        String returner = "";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            restaurant = (List<Restaurant>) session.createQuery("from Restaurant").list();
            session.getTransaction().commit();
            session.close(); // kanske skall kommenteras bort
        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }

        for (int i = 0; i < restaurant.size(); i++) {
            Restaurant pr = restaurant.get(i);
           returner+=" <option value='"+pr.getId()+"'>"+pr.getName()+"</option>";
        }

        return returner;
  
}

}

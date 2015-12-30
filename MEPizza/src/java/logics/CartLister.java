/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

import hibernate.HibernateUtil;
import hibernate.Cart;
import hibernate.Product;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author Magnus Kanfjäll
 */
@Stateless
public class CartLister implements CartListerLocal {

   @Override
   public String cartContensAsHtmlRow(int userId){
     
        Session session = null;
        List<Cart> theCart =null;
        String retuner="";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                   
            theCart =(List<Cart>) session.createQuery("select cart from Cart cart where cart.userId = :userId").setParameter("userId", userId).list();
            session.getTransaction().commit();
        int total=0;
        
        for(int i=0; i<theCart.size(); i++){
            Cart pr = theCart.get(i);
            
            session.beginTransaction();
            Product Product = (Product) session.createQuery("select product from Product product where product.id = :id")
                    .setParameter("id", pr.getProductId())
                    .uniqueResult();
            session.getTransaction().commit();
            
            
            
            retuner+="<tr>";
            
            retuner+="<td>"+Product.getName()+"</td>";
            retuner+="<td>1st</td>";
            retuner+="<td>"+Product.getPrice()+"kr</td>";
            
            retuner+="</tr>";
            
            total+=Product.getPrice();
            
        }
        retuner+="<tr class='total'><td colspan='2'>Total:</td><td>"+total+"kr</td><tr>";
        
        
        session.close();  
        
         } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }
      return retuner;
   }
}

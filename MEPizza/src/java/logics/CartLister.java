/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

import hibernate.HibernateUtil;
import hibernate.CartId;
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
        List<CartId> theCart =null;
        String retuner=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                   
            theCart =(List<CartId>) session.createQuery("select CartId from CartId").list();
            session.getTransaction().commit();
            session.close(); // kanske skall kommenteras bort
        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }
        
        
        
        
        for(int i=0; i<theCart.size(); i++){
            retuner+="<tr>";
            CartId pr = theCart.get(i);
            retuner+="<td>"+pr.getProductId()+"</td>";
            
            
            retuner+="</tr>";
        }
        
      return retuner;
   }
}

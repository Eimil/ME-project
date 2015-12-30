/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logics;

import hibernate.HibernateUtil;
import hibernate.Product;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author magnu_000
 */
@Stateless
public class ProductLister implements ProductListerLocal {

  @Override
  public String getProductsAsHtmlRows(){
      
        Session session = null;
        List<Product> products =null;
        String retuner="";;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            products =(List<Product>) session.createQuery("from Product").list();
            session.getTransaction().commit();
            session.close(); // kanske skall kommenteras bort
        } catch (Exception ex) {
            System.out.println("Exception in finding account : " + ex);
        }
        
        for(int i=0; i<products.size(); i++){
            retuner+="<tr>";
            Product pr = products.get(i);
            retuner+="<td>"+pr.getName()+"</td>";
            retuner+="<td>"+pr.getDescription()+"</td>";
            retuner+="<td><img src=\""+pr.getPicLink()+"\" height=\"42\" width=\"42\"></td>";
            retuner+="<td>"+pr.getPrice()+"</td>";
            retuner+="<td><a href=\""+pr.getId()+"\">Lägg till kundvagn</a></td>";
            
            retuner+="</tr>";
        }
        
      return retuner;
  }
}

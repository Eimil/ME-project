package logics;

/*
 * Local interface for the CartHandler bean.
 */

import javax.ejb.Local;

/**
 *
 * @author Magnus Kanfjäll
 */
@Local
public interface CartHandlerLocal {

    public void addToCart(int productId, int userId);

    public void removeFromCart(int productId, int userId);

    public String resturantDropdownHtml();
    
}

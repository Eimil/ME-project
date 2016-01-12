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

    public boolean addToCart(int productId, int userId);

    public boolean removeFromCart(int rowId, int userId);

    public String resturantDropdownHtml();
    
}

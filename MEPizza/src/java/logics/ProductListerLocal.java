package logics;

/*
 * Local interface for the ProductLister bean.
 */

import javax.ejb.Local;

/**
 *
 * @author Magnus Kanfjäll
 */
@Local
public interface ProductListerLocal {

    public String getProductsAsHtmlRows();
    
}

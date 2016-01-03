package logics;

import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface ProductListerLocal {

    String listAvaliableProducts();
    
}

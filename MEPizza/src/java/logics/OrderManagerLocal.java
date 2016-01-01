package logics;

/*
 * Local interface for the OrderManager bean.
 */

import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface OrderManagerLocal {

    String createOrder(String [] orderValues);
    
}

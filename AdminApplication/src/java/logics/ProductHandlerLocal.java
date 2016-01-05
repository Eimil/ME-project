package logics;

/*
 * Local interface for the ProductHandler bean.
 */
import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface ProductHandlerLocal {

    boolean addProduct(String[] productInfo);

    boolean removeProduct(int productId);

}

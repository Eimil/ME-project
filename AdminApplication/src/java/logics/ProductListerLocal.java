package logics;

/*
 * Local interface for the ProductLister bean.
 */
import javax.ejb.Local;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface ProductListerLocal {

    String listAvaliableProducts();

}

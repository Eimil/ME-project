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

    public String getPurchasesAsHtmlRows(int userId);

    public void setStatus(int id, String status);

}

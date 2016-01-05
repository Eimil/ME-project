package logics;

/*
 * Local interface for the PurchaseHistoryManager bean.
 */
import javax.ejb.Local;

/**
 *
 * @author Magnus Kanfjäll
 */
@Local
public interface PurchaseHistoryManagerLocal {

    public String getPurchasesAsHtmlRows(int userId);

}

package logics;

/*
 * Local interface for the Utils bean.
 */
import javax.ejb.Local;

/**
 *
 * @author Magnus
 */
@Local
public interface UtilsLocal {

    public int getStoreIdByUserId(int userId);

    public String translateStatus(String key, String lang);

}

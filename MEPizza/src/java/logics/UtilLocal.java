package logics;

/*
 * Local interface for the Util bean.
 */
import javax.ejb.Local;

/**
 *
 * @author Magnus Kanfjäll
 */
@Local
public interface UtilLocal {

    public String translateStatus(String key, String lang);

}

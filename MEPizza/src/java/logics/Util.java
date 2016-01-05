package logics;

/*
* The Stateless Session Bean which handles the translation of an orders status.
 */
import javax.ejb.Stateless;

/**
 *
 * @author Magnus Kanfjäll
 */
@Stateless
public class Util implements UtilLocal {

    @Override
    public String translateStatus(String key, String lang) {

        switch (lang) {
            case "swe":
                switch (key) {
                    case "new":
                        return "Ny order";
                    case "finished":
                        return "Avslutad";
                    case "erased":
                        return "Makulerad";
                    default:
                        break;
                }
            default:
                return key;
        }

    }
}

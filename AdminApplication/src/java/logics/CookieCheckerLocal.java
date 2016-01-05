package logics;

/*
 * Local interface for the CookieChecker bean.
 */
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Emil Ejder
 */
@Local
public interface CookieCheckerLocal {

    List checkIfCookieExists(HttpServletRequest request, HttpServletResponse response);

}

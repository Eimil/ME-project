package logics;

/*
* The Stateless Session Bean which handles the checking if a client has a cookie.
 */
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Emil Ejder
 */
@Stateless
public class CookieChecker implements CookieCheckerLocal {

    @Override
    public List checkIfCookieExists(HttpServletRequest request, HttpServletResponse response) {
        List<Object> list = new ArrayList<Object>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mePizzaAdmin")) {
                    String userID = cookie.getValue();
                    cookie.setMaxAge(15 * 60);
                    cookie.setSecure(true);
                    response.addCookie(cookie);
                    list.add(response);
                    list.add(userID);
                }
            }
        }
        return list;
    }
}

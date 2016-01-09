package servlets;

/*
*  The servlet acting as a controller for the purpose of logging an account in
*   Reads the inputed parametres and calls the responsible bean to act.
 */
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.LoginCheckerLocal;

/**
 *
 * @author Emil Ejder
 */
public class LoginServlet extends HttpServlet {

    /*
    *   The reference to the EJB used to logging in an account
     */
    @EJB
    private LoginCheckerLocal loginChecker;

    /*
    *   Method which handles the GET request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /*
    *   Method which handles the GET request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && username.length() > 3) {
            if (password != null && password.length() > 3) {
                String hashedUser = loginChecker.hashString(username);
                String hashedPass = loginChecker.hashString(password);
                String result = loginChecker.checkIfValid(hashedUser, hashedPass);
                if (!result.equals("BAD")) {
                    Cookie meCookie = new Cookie("mePizzaUser", result);
                    meCookie.setMaxAge(15 * 60);
                    meCookie.setSecure(true);
                    response.addCookie(meCookie);
                    response.sendRedirect("StoreServlet");
                } else {
                    request = setError(request, "Kunde inte logga in", "Felaktiga uppgifter");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        }
    }

    /*
    *   Method which is called to set the error parametres later displayed to client.
     */
    private HttpServletRequest setError(HttpServletRequest request, String error, String reason) {
        request.setAttribute("error", error);
        request.setAttribute("page", "login.jsp");
        request.setAttribute("reason", reason);
        return request;
    }
}

package controllers;

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

    @EJB
    private LoginCheckerLocal loginChecker;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

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
                if (loginChecker.checkIfValid(hashedUser, hashedPass).equals("GOOD")) {
                    Cookie meCookie = new Cookie("mePizzaUser", "true");
                    meCookie.setMaxAge(15 * 60);
                    //meCookie.setSecure(true); skickas endast med endast ssl / https
                    response.addCookie(meCookie);
                    response.sendRedirect("StoreController");
                } else {
                    request = setError(request, "Kunde inte logga in", "Felaktiga uppgifter");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        }
    }

    private HttpServletRequest setError(HttpServletRequest request, String error, String reason) {
        request.setAttribute("error", error);
        request.setAttribute("page", "login.jsp");
        request.setAttribute("reason", reason);
        return request;
    }
}

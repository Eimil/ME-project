/*
*  The servlet acting as a controller for the purpose of showing previous purchases
*   Reads the inputed parametres and calls the responsible bean to act.
 */
package servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.PurchaseHistoryManagerLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class PurchaseHistoryServlet extends HttpServlet {

    /*
    *   The reference to the EJB used to list earlier made purchases.
     */
    @EJB
    private PurchaseHistoryManagerLocal purchaseHistoryManager;

    /*
    * Method which handles the GET requests.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mePizzaUser")) {
                    userID = cookie.getValue();
                    cookie.setMaxAge(15 * 60);
                    response.addCookie(cookie);
                }
            }
        }
        if (userID == null) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("rows", purchaseHistoryManager.getPurchasesAsHtmlRows(Integer.parseInt(userID)));
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a>/<a href='SettingsServlet'>Kontouppgifter</a></h3>");;
            request.getRequestDispatcher("history.jsp").forward(request, response);
        }

    }

    /*
    * Method which handles the POST requests.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}

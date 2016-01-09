/*
*  The servlet acting as a controller for the purpose of showing previous purchases
*   Reads the inputed parametres and calls the responsible bean to act.
 */
package servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.CookieCheckerLocal;
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
    *   The reference to the EJB used to check if a valid cookie exists.
     */
    @EJB
    private CookieCheckerLocal cookieChecker;

    /*
    * Method which handles the GET requests.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userID = null;
        List<Object> list = cookieChecker.checkIfCookieExists(request, response);
        if (list != null && !list.isEmpty()) {
            if (list.get(0) != null) {
                response = (HttpServletResponse) list.get(0);
            }
            if (list.get(1) != null) {
                userID = (String) list.get(1);
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

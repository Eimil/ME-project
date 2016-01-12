package servlets;

/*
*  The servlet acting as a controller for the purpose of handling orders.
*   Reads the inputed parametres and calls the responsible bean to act.
 */
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.CookieCheckerLocal;
import logics.OrderManagerLocal;
import logics.UtilsLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class OrderServlet extends HttpServlet {

    /*
    *   The reference to the EJB used to check if a valid cookie exists.
     */
    @EJB
    private CookieCheckerLocal cookieChecker;

    /*
    *   The reference to the EJB used to translate orderstatus/storeId
     */
    @EJB
    private UtilsLocal utils;

    /*
    *   The reference to the EJB used to manage orders.
     */
    @EJB
    private OrderManagerLocal orderManager;

    /*
    *   Method which handles the GET request.
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
            request.setCharacterEncoding("UTF-8");
            int storeId = utils.getStoreIdByUserId(Integer.parseInt(userID));
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a></h3>");
            request.setAttribute("rows", orderManager.getPurchasesAsHtmlRows(storeId));
            request.getRequestDispatcher("orders.jsp").forward(request, response);
        }

    }

    /*
    *   Method which handles the POST request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
            String removeButton = request.getParameter("removeButton");
            String finishButton = request.getParameter("finishButton");
            int id = Integer.parseInt(request.getParameter("id"));
            if (finishButton != null && finishButton.length() > 3) {
                orderManager.setStatus(id, "finished");

                //Send email
            } else if (removeButton != null && removeButton.length() > 3) {
                orderManager.setStatus(id, "erased");
            }
            doGet(request, response);
        }
    }
}

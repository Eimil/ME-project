package servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.OrderManagerLocal;
import logics.UtilsLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class OrderServlet extends HttpServlet {

    @EJB
    private UtilsLocal utils;

    @EJB
    private OrderManagerLocal orderManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mePizzaAdmin")) {
                    userID = cookie.getValue();
                    cookie.setMaxAge(15 * 60);
                    response.addCookie(cookie);
                }
            }
        }
        if (userID == null) {
            response.sendRedirect("login.jsp");
        } else {
            request.setCharacterEncoding("UTF-8");
            int storeId= utils.getStoreIdByUserId(Integer.parseInt(userID));
            request.setAttribute("rows", orderManager.getPurchasesAsHtmlRows(storeId));
            request.getRequestDispatcher("orders.jsp").forward(request, response);
        }

    }

}

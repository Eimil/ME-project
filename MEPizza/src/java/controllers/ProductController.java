package controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.ProductListerLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class ProductController extends HttpServlet {

    @EJB
    private ProductListerLocal productLister;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userID = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mePizzaUser")) {
                    userID = cookie.getValue();
                }
            }
        }
        if (userID == null) {
            response.sendRedirect("login.jsp");
        } else {
            String rows = productLister.getProductsAsHtmlRows();
            request.setAttribute("rows", rows);
            request.getRequestDispatcher("store.jsp").forward(request, response);
        }

    }

    private HttpServletRequest setError(HttpServletRequest request, String error, String reason) {
        request.setAttribute("error", error);
        request.setAttribute("page", "accountsettings.jsp");
        request.setAttribute("reason", reason);
        return request;
    }
}

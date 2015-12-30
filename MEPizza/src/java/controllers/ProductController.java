package controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.CartListerLocal;
import logics.ProductListerLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class ProductController extends HttpServlet {

    @EJB
    private CartListerLocal cartLister;

    @EJB
    private ProductListerLocal productLister;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            
            String products = productLister.getProductsAsHtmlRows();
            String cart = cartLister.cartContensAsHtmlRow(Integer.parseInt(userID));
            
            request.setAttribute("products", products);
            request.setAttribute("cart", cart);
            request.setAttribute("infobox", "<h3>Inloggad som ID:"+userID+"</h3>");
            request.getRequestDispatcher("store.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie loginCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("mePizzaUser")) {
                    loginCookie = cookie;
                    break;
                }
            }
        }
        if (loginCookie != null) {
            loginCookie.setMaxAge(0);
            response.addCookie(loginCookie);
        }
        response.sendRedirect("login.jsp");
    }
}

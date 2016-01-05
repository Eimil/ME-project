package servlets;

/*
*  The servlet acting as a controller for the purpose of the shopping part
*   Reads the inputed parametres and calls the responsible bean to act.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.CartHandlerLocal;
import logics.CartListerLocal;
import logics.CookieCheckerLocal;
import logics.ProductListerLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class StoreServlet extends HttpServlet {

    @EJB
    private CartHandlerLocal cartHandler;

    /*
    *   The reference to the EJB used to list the items in the cart
     */
    @EJB
    private CartListerLocal cartLister;

    /*
    *   The reference to the EJB used to list the avaliable products
     */
    @EJB
    private ProductListerLocal productLister;

    /*
    *   The reference to the EJB used to check if a valid cookie exists.
     */
    @EJB
    private CookieCheckerLocal cookieChecker;

    /*
    *   Method which handles the GET request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userID = null;
        List<Object> list = new ArrayList<>();
        list = cookieChecker.checkIfCookieExists(request, response);
        response = (HttpServletResponse) list.get(0);
        userID = (String) list.get(1);
        if (userID == null) {
            response.sendRedirect("login.jsp");
        } else {
            String products = productLister.getProductsAsHtmlRows();
            String[] cartResult = cartLister.cartContensAsHtmlRow(Integer.parseInt(userID));
            request.setAttribute("products", products);
            request.setAttribute("cart", cartResult[0]);
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a>/<a href='SettingsServlet'>Kontouppgifter</a></h3>");
            request.getRequestDispatcher("store.jsp").forward(request, response);
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
        List<Object> list = new ArrayList<>();
        list = cookieChecker.checkIfCookieExists(request, response);
        response = (HttpServletResponse) list.get(0);
        userID = (String) list.get(1);
        //Add to cart
        int id = Integer.parseInt(request.getParameter("id"));
        cartHandler.addToCart(id, Integer.parseInt(userID));
        if (userID == null) {
            response.sendRedirect("login.jsp");
        } else {
            String products = productLister.getProductsAsHtmlRows();
            String[] cartResult = cartLister.cartContensAsHtmlRow(Integer.parseInt(userID));
            request.setAttribute("products", products);
            request.setAttribute("cart", cartResult[0]);
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a>/<a href='SettingsServlet'>Kontouppgifter</a></h3>");
            request.getRequestDispatcher("store.jsp").forward(request, response);
        }

    }
}

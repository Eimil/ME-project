package controllers;

/*
*  The servlet acting as a controller for the purpose of the payment of an order
*   Reads the inputed parametres and calls the responsible bean to act.
 */
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.CartHandlerLocal;
import logics.CartListerLocal;
import logics.OrderManagerLocal;
import logics.ProductListerLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class CheckoutController extends HttpServlet {

    @EJB
    private OrderManagerLocal orderManager;

    private String[] formInfo = new String[]{"notes", "cardOwner", "csv", "expireMM", "expireYY", "cardnumber", "restaurant", "price"};
    private String[] readInfo = new String[9];

    @EJB
    private CartHandlerLocal cartHandler;

    /*
    *   The reference to the EJB used to list the avaliable products
     */
    @EJB
    private ProductListerLocal productLister;

    /*
    *   The reference to the EJB used to list the items in the cart
     */
    @EJB
    private CartListerLocal cartLister;

    /*
    *   Method which handles the GET request.
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

            String[] cartResult = cartLister.cartContensAsHtmlRow(Integer.parseInt(userID));
            int price = Integer.parseInt(cartResult[1]);
            String dropdown = cartHandler.resturantDropdownHtml();
            request.setAttribute("cart", cartResult[0]);
            request.setAttribute("price", cartResult[1]);
            request.setAttribute("dropdown", dropdown);
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3>");
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
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
            for (int i = 0; i < formInfo.length; i++) {
                if (request.getParameter(formInfo[i]) != null && formInfo[i].length() > 2) {
                    readInfo[i] = request.getParameter(formInfo[i]);
                } else {
                    readInfo[i] = null;
                }
            }
            readInfo[8] = "" + userID;
            String result = orderManager.createOrder(readInfo);
            if (result.equalsIgnoreCase("CREATED & MAILED")) {
                response.sendRedirect("PurchaseHistoryController");
            } else {
                // you did bad son
            }
            
        }
    }

    private HttpServletRequest setError(HttpServletRequest request, String error, String reason) {
        request.setAttribute("error", error);
        request.setAttribute("page", "createaccount.jsp");
        request.setAttribute("reason", reason);
        return request;
    }
}

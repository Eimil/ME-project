package servlets;

/*
*  The servlet acting as a controller for the purpose of the payment of an order
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
import logics.OrderManagerLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class CheckoutServlet extends HttpServlet {

    @EJB
    private OrderManagerLocal orderManager;

    private final String[] formInfo = new String[]{"notes", "cardOwner", "csv", "expireMM", "expireYY", "cardnumber", "restaurant", "price"};
    private String[] readInfo = new String[9];
    private String[] cartResult;

    @EJB
    private CartHandlerLocal cartHandler;

    /*
    *   The reference to the EJB used to list the items in the cart
     */
    @EJB
    private CartListerLocal cartLister;

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
        String userID = null;
        List<Object> list = new ArrayList<>();
        list = cookieChecker.checkIfCookieExists(request, response);
        response = (HttpServletResponse) list.get(0);
        userID = (String) list.get(1);
        if (userID == null) {
            response.sendRedirect("login.jsp");
        } else {
            request = loadPageContent(request, response, userID);
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }

    /*
    *   Method which handles the POST request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = null;
        List<Object> list = new ArrayList<>();
        list = cookieChecker.checkIfCookieExists(request, response);
        response = (HttpServletResponse) list.get(0);
        userID = (String) list.get(1);
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
            if (cartResult[0].equals("<tr class='total'><td colspan='2'>Total:</td><td>0kr</td><tr>")) { // In case there is no products in cart
                request = loadPageContent(request, response, userID);
                request.setAttribute("purchaseResult", "Purchase failed, no items in cart");
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
            } else { // If there is products in the cart.
                String result = orderManager.createOrder(readInfo);
                if (result.equalsIgnoreCase("CREATED & MAILED")) {
                    response.sendRedirect("PurchaseHistoryServlet");
                } else {
                    request = loadPageContent(request, response, userID);
                    request.setAttribute("purchaseResult", "Purchase failed, couldn't complete order/ send mail");
                    request.getRequestDispatcher("checkout.jsp").forward(request, response);
                }
            }
        }
    }

    public HttpServletRequest loadPageContent(HttpServletRequest request, HttpServletResponse response, String userID) {
        cartResult = cartLister.cartContensAsHtmlRow(Integer.parseInt(userID));
        String userInfo = cartLister.getUserInfo(Integer.parseInt(userID));
        int price = Integer.parseInt(cartResult[1]);
        String dropdown = cartHandler.resturantDropdownHtml();
        request.setAttribute("userInfo", userInfo);
        request.setAttribute("cart", cartResult[0]);
        request.setAttribute("price", cartResult[1]);
        request.setAttribute("dropdown", dropdown);
        request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a>/<a href='SettingsServlet'>Kontouppgifter</a></h3>");
        return request;
    }
}

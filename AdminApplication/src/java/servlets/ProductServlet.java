package servlets;

/*
*  The servlet acting as a controller for the purpose of handling products.
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
import logics.ProductHandlerLocal;
import logics.ProductListerLocal;

public class ProductServlet extends HttpServlet {

    private final String[] productParams = new String[]{"name", "description", "pictUrl", "price"};
    private final String[] savedParams = new String[4];

    /*
    *   The reference to the EJB used to handle products.
     */
    @EJB
    private ProductHandlerLocal productHandler;

    /*
    *   The reference to the EJB used to check if a valid cookie exists.
     */
    @EJB
    private CookieCheckerLocal cookieChecker;

    /*
    *   The reference to the EJB used to list the products.
     */
    @EJB
    private ProductListerLocal productLister;

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
            String products = productLister.listAvaliableProducts();
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a></h3>");
            request.setAttribute("products", products);
            request.getRequestDispatcher("products.jsp").forward(request, response);
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
            String addButton = request.getParameter("addButton");
            String removeButton = request.getParameter("removeButton");
            if (addButton != null && addButton.length() > 3) {
                boolean isAllFilled = true;
                for (int i = 0; i < productParams.length; i++) {
                    if (request.getParameter(productParams[i]) != null && request.getParameter(productParams[i]).length() > 1) {
                        savedParams[i] = request.getParameter(productParams[i]);
                    } else {
                        isAllFilled = false;
                        break;
                    }
                }
                if (isAllFilled) {
                    if (productHandler.addProduct(savedParams)) {
                        // Köp genomfördes, refresha sidan
                    } else {
                        request.setAttribute("error", "Kunde inte lägga till produkt");
                    }
                    String products = productLister.listAvaliableProducts();
                    request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a>/<a href='SettingsServlet'>Kontouppgifter</a></h3>");
                    request.setAttribute("products", products);
                    request.getRequestDispatcher("products.jsp").forward(request, response);
                }
            }
            if (removeButton != null && removeButton.length() > 3) {
                String productId = request.getParameter("id");
                if (productId != null && productId.length() > 0) {
                    if (productHandler.removeProduct(Integer.parseInt(productId))) {
                        // Bortttagning genomfördes, refresha sidan
                    } else {
                        request.setAttribute("error", "Kunde inte ta bort produkt");
                    }
                    String products = productLister.listAvaliableProducts();
                    request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'></h3>");
                    request.setAttribute("products", products);
                    request.getRequestDispatcher("products.jsp").forward(request, response);
                }
            }
        }
    }
}

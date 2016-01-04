package servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.ProductHandlerLocal;
import logics.ProductListerLocal;

public class ProductServlet extends HttpServlet {

    private final String[] productParams = new String[]{"name", "description", "pictUrl", "price"};
    private final String[] savedParams = new String[4];

    @EJB
    private ProductHandlerLocal productHandler;

    @EJB
    private ProductListerLocal productLister;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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
            String products = productLister.listAvaliableProducts();
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a>/<a href='SettingsServlet'>Kontouppgifter</a></h3>");
            request.setAttribute("products", products);
            request.getRequestDispatcher("products.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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
                    request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a>/<a href='SettingsServlet'>Kontouppgifter</a></h3>");
                    request.setAttribute("products", products);
                    request.getRequestDispatcher("products.jsp").forward(request, response);
                }
            }
        }
    }
}

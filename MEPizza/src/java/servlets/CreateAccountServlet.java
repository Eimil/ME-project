package servlets;

/*
*  The servlet acting as a controller for the purpose of creating accounts
*   Reads the inputed parametres and calls the responsible bean to act.
 */
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.AccountCreatorLocal;

/**
 *
 * @author Emil Ejder
 */
public class CreateAccountServlet extends HttpServlet {

    /*
    *   The reference to the EJB used to create a new account
     */
    @EJB
    private AccountCreatorLocal accountCreator;

    /*
    *   Method which handles the GET request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("createaccount.jsp").forward(request, response);
    }

    /*
    *   Method which handles the POST request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String address = request.getParameter("address");
        String zipcode = request.getParameter("zipcode");
        String password = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String[] accountParams = new String[]{username, fullname, address, zipcode, password, password2, email, phone};
        boolean isAllFilled = true;
        for (String accountParam : accountParams) {
            if (accountParam == null || accountParam.equals("")) {
                isAllFilled = false;
                break;
            }
        }
        if (isAllFilled) {
            if (password.equals(password2)) {
                String hashedUsername = accountCreator.hashString(username);
                String hashedPassword = accountCreator.hashString(password);
                String[] creationParams = new String[]{hashedUsername, fullname, address, zipcode, hashedPassword, email, phone};
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain");
                if (accountCreator.checkIfCredsAvaliable(hashedUsername, email).equalsIgnoreCase("OK")) {
                    if (accountCreator.createAccount(creationParams)) {
                        response.sendRedirect(response.encodeRedirectURL("login.jsp"));
                    } else {
                        request = setError(request, "Kunde inte skapa konto", "Internt fel");
                        request.getRequestDispatcher("createaccount.jsp").forward(request, response);
                    }
                } else {
                    request = setError(request, "Kunde inte skapa konto", "Kontouppgifter upptagna");
                    request.getRequestDispatcher("createaccount.jsp").forward(request, response);
                }
            } else {
                request = setError(request, "Kunde inte skapa konto", "Lösenorden matchar inte");
                request.getRequestDispatcher("createaccount.jsp").forward(request, response);
            }
        } else {
            request = setError(request, "Kunde inte skapa konto", "Fattas input");
            request.getRequestDispatcher("createaccount.jsp").forward(request, response);
        }
    }

    /*
    *   Method which is called to set the error parametres later displayed to client.
     */
    private HttpServletRequest setError(HttpServletRequest request, String error, String reason) {
        request.setAttribute("error", error);
        request.setAttribute("page", "createaccount.jsp");
        request.setAttribute("reason", reason);
        return request;
    }
}

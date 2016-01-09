package servlets;

/*
*  The servlet acting as a controller for the purpose of changing account info
*   Reads the inputed parametres and calls the responsible bean to act.
 */
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.AccountInfoChangerLocal;
import logics.CookieCheckerLocal;

/**
 *
 * @author Emil Ejder
 */
public class SettingsServlet extends HttpServlet {

    private final List<String> infoMapping = Arrays.asList("fullname", "address", "zipcode", "password", "passwordNew", "passwordNew2", "email", "phone");
    private String[] userInfo;
    private String[] newUserInfo;
    private String[] composedUserInfo;

    /*
    *   The reference to the EJB used to change the account info
     */
    @EJB
    private AccountInfoChangerLocal accountInfoChanger;

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
        doPost(request, response);
    }

    /*
    *   Method which handles the POST request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String changeButton = request.getParameter("changeButton");
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
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3><h3><a href='LogoutServlet'>Logga ut</a>/<a href='SettingsServlet'>Kontouppgifter</a></h3>");
            userInfo = accountInfoChanger.loadUserInfo(userID);
            if (changeButton != null && changeButton.length() > 3) {
                newUserInfo = processUserInput(request);
                if (newUserInfo[3] != null && newUserInfo[4] != null && newUserInfo[5] != null) { // If you have chosen to change password
                    if (accountInfoChanger.checkPassword(accountInfoChanger.hashString(newUserInfo[3]))) { // If the old password is incorrect
                        if (!IsPasswordsMatching(newUserInfo[4], newUserInfo[5])) {
                            request = setError(request, "Kunde inte byta lösnord", "Lösenorden matchar inte");
                            loadPage(request, response);
                        }
                    } else {
                        request = setError(request, "Kunde inte byta lösenord", "Felaktigt nuvarande lösenord");
                        loadPage(request, response);
                    }
                } else if (newUserInfo[3] != null || newUserInfo[4] != null || newUserInfo[5] != null) { // If some of the password fields had no input
                    request = setError(request, "Kunde inte byta lösenord", "Saknas input");
                    loadPage(request, response);
                }
                composeNewInfo();
                if (!accountInfoChanger.changeUserInfo(composedUserInfo, userID).equalsIgnoreCase("good")) {
                    request = setError(request, "Kunde inte modifiera kontot", "Internt fel");
                }
                boolean isFieldsEmpty = false;
                for (String newUserInfo1 : newUserInfo) {
                    if (newUserInfo1 != null && newUserInfo1.length() >= 5) {
                        isFieldsEmpty = false;
                    } else {
                        isFieldsEmpty = true;
                    }
                }
                if (isFieldsEmpty) {
                    userInfo = accountInfoChanger.loadUserInfo(userID);
                    request.setAttribute("result", "Kontouppgifterna har ändrats!");
                    loadPage(request, response);
                } else {
                    userInfo = accountInfoChanger.loadUserInfo(userID);
                    request.setAttribute("result", "Inga fält ifyllda, ingen data ändrad.");
                    loadPage(request, response);
                }
            } else {
                loadPage(request, response);
            }
        }
    }

    /*
    *   Method which is called to read the clients inputted parametres
     */
    private String[] processUserInput(HttpServletRequest request) {
        String newInfo[] = new String[8];
        for (int i = 0; i < newInfo.length; i++) {
            if (request.getParameter(infoMapping.get(i)) != null && !request.getParameter(infoMapping.get(i)).equals("")) {
                newInfo[i] = request.getParameter(infoMapping.get(i));
            }
        }
        return newInfo;
    }

    /*
    *   Method which is called to check if the inputed passwords matches
     */
    private boolean IsPasswordsMatching(String password, String passwordRepeated) {
        return password.equals(passwordRepeated);
    }

    /*
    *   Method which is called to compress values into a smaller array
     */
    private void composeNewInfo() {
        composedUserInfo = new String[6];
        composedUserInfo[0] = newUserInfo[0]; // name
        composedUserInfo[1] = newUserInfo[1]; // address
        composedUserInfo[2] = newUserInfo[2]; // zipcode
        composedUserInfo[3] = newUserInfo[4]; // new password
        composedUserInfo[4] = newUserInfo[6]; // email
        composedUserInfo[5] = newUserInfo[7]; // phone
    }

    /*
    *   Method which is called to set the error parametres later displayed to client.
     */
    private HttpServletRequest setError(HttpServletRequest request, String error, String reason) {
        request.setAttribute("error", error);
        request.setAttribute("page", "accountsettings.jsp");
        request.setAttribute("reason", reason);
        return request;
    }

    /*
    *   Method which is called to load the page with the user parametres
     */
    private void loadPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("fullname", userInfo[0]);
        request.setAttribute("address", userInfo[1]);
        request.setAttribute("zipcode", userInfo[2]);
        request.setAttribute("email", userInfo[3]);
        request.setAttribute("phone", userInfo[4]);
        request.getRequestDispatcher("accountsettings.jsp").forward(request, response);
    }
}

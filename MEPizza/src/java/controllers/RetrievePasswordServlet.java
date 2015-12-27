package controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.PasswordRetrievalLocal;

/**
 *
 * @author Emil Ejder
 */
public class RetrievePasswordServlet extends HttpServlet {

    private final int length = 20;

    @EJB
    private PasswordRetrievalLocal passwordRetrieval;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
            
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        if (email != null && !email.equals("")) {
            String genString = "d35kaBedpqsSaiNUSktyZvmQLWorI";
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            // Kollab så att account med den emailen existerar sen dra en if runt resten
            String randomText = passwordRetrieval.generateRandomString(genString, length);
            String hashedPass = passwordRetrieval.hashPassword(randomText);
            if (passwordRetrieval.setNewPassword(hashedPass, email)) {
                if (passwordRetrieval.sendMailToUser(email, randomText)) {
                    response.sendRedirect(response.encodeRedirectURL("login.jsp"));
                } else {
                    request = setError(request, "Kunde inte skicka ut mail", "Internt fel");
                    request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
                }
            } else {
                request = setError(request, "Kunde inte sätta ett nytt lösnord", "Email existerar inte");
                request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
            }
        }
    }

    private HttpServletRequest setError(HttpServletRequest request, String error, String reason) {
        request.setAttribute("error", error);
        request.setAttribute("page", "forgotpassword.jsp");
        request.setAttribute("reason", reason);
        return request;
    }

}

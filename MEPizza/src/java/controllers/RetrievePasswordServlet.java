package controllers;

import java.io.IOException;
import java.util.Random;
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

    private Random random;
    private int length = 20;

    @EJB
    private PasswordRetrievalLocal passwordRetrieval;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        System.out.println("The email is : " + email);
        // Kolla så att account med den emailen existerar sen dra en if runt resten
        String randomText = passwordRetrieval.generateRandomString(random, email, length);
        String hashedPass = passwordRetrieval.hashPassword(randomText);
        // Insert to DB at account med email = email;
        boolean result = passwordRetrieval.sendMailToUser(email, randomText);
        if (result) {
            // Confirma att mail är skickat
        } else {
            // Visa ett error
        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.PurchaseHistoryManagerLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class PurchaseHistoryController extends HttpServlet {

    @EJB
    private PurchaseHistoryManagerLocal purchaseHistoryManager;



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

            
            request.setAttribute("rows", purchaseHistoryManager.getPurchasesAsHtmlRows(Integer.parseInt(userID)));
            request.setAttribute("infobox", "<h3>Inloggad som ID:" + userID + "</h3>");
            request.getRequestDispatcher("history.jsp").forward(request, response);
        }
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

   

}

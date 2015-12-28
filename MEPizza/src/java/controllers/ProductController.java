package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logics.ProductListerLocal;

/**
 *
 * @author Magnus Kanfjäll
 */
public class ProductController extends HttpServlet {

    @EJB
    private ProductListerLocal productLister;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String rows =productLister.getProductsAsHtmlRows();

        request.setAttribute("rows", rows);
        request.getRequestDispatcher("store.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}

package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class PaymentServlet extends HttpServlet implements Servlet {
    public PaymentServlet() {}
    protected void doGet (HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        request.setAttribute("itemID", (String)session.getAttribute("itemID"));
        request.setAttribute("name", (String)session.getAttribute("name"));
        request.setAttribute("buyPrice", (String)session.getAttribute("buyPrice"));
        request.getRequestDispatcher("/payment.jsp").forward(request, response);
    }
}

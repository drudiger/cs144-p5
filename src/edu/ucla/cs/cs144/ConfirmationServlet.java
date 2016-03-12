package edu.ucla.cs.cs144;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ConfirmationServlet extends HttpServlet implements Servlet {
    public ConfirmationServlet() {}
    protected void doPost (HttpServletRequest request, HttpServletResponse response) 
                           throws ServletException, IOException {

    HttpSession session = request.getSession(true);
    
    Date time = new Date();

    request.setAttribute("itemID", (String)session.getAttribute("itemID"));
    request.setAttribute("name", (String)session.getAttribute("name"));
    request.setAttribute("buyPrice", (String)session.getAttribute("buyPrice"));
    request.setAttribute("creditCardNum", (String)request.getParameter("creditCardNum"));
    request.setAttribute("time", DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(time));

    request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
  }
}

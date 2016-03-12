package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.cs.cs144.AuctionSearchClient;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Parse Request
        String q;
        int numResultsToSkip, numResultsToReturn;
        q = request.getParameter("q");
        numResultsToSkip = Integer.parseInt(request.getParameter("numResultsToSkip"));
        numResultsToReturn = Integer.parseInt(request.getParameter("numResultsToReturn"));
        
        // Get Data
        SearchResult[] results = AuctionSearchClient.basicSearch(q,numResultsToSkip,numResultsToReturn);
        
        // Make Page
        request.setAttribute("results", results);
        request.setAttribute("query", q);
        request.setAttribute("current", numResultsToSkip);
        request.setAttribute("amount", numResultsToReturn);
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }
}

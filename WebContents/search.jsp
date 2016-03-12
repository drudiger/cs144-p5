 <%@ page import="edu.ucla.cs.cs144.SearchResult" %>
 <html>
    <head><title>eBay Search</title></head>
    <body>
    <%@ include file="keywordSearch.html" %>
    <%
    SearchResult[] results = (SearchResult[])request.getAttribute("results");
    out.println("<table><tr><td>itemId</td><td>Name</td></tr>");
    for(int i = 0; i < results.length; i++){
        out.println("<tr><td><a href='item?id="+results[i].getItemId()+"'>"+results[i].getItemId()+"</a></td>");
        out.println("<td>"+results[i].getName()+"</td></tr>");
    }
    out.println("</table>");
    String query = (String)request.getAttribute("query");
    int current = (Integer)request.getAttribute("current");
    int amount = (Integer)request.getAttribute("amount");
    if (current >= amount)
        out.println("<a href='search?q="+query+"&numResultsToSkip="+Integer.toString(current-amount)+"&numResultsToReturn="+Integer.toString(amount)+"'>Previous</a>&nbsp");
    out.println("<a href='search?q="+query+"&numResultsToSkip="+Integer.toString(current+amount)+"&numResultsToReturn="+Integer.toString(amount)+"'>Next</a>&nbsp");
    %>
  </body>
</html>

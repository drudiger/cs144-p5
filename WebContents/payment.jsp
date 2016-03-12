<% String URL = "https://" + request.getServerName()+":8443"+request.getContextPath()+"/confirmation"; %>
<html>
    <head> 
        <title>Enter Payment Info</title>
    </head>
	<body>
        <h2>Please enter your credit card number for payment</h2>
        <form method="POST" action="<%= URL %>">
            <ul>
                <li>Item ID: <%= request.getAttribute("itemID") %></li>
                <li>Name: <%= request.getAttribute("name") %></li>
                <li>Buy Price: <%= request.getAttribute("buyPrice") %></li>      
            </ul>
            <p>Credit Card Number:
                <input type="text" name="creditCardNum" />
                <input type="submit" value="Submit" /> 
            </p>
        </form>
	</body>
</html>
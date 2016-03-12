<!DOCTYPE html> 
<html>   
<head>
	<title>Confirmation</title>
    <body>
	    <h2>You've successfully placed your order!</h2>
        	<ul>
        		<li>Item ID: <%= request.getAttribute("itemID") %></li>
             	<li>Item Name: <%= request.getAttribute("name") %></li>
                <li>Price: <%= request.getAttribute("buyPrice") %></li> 
                <li>Credit Card Number: <%= request.getAttribute("creditCardNum") %></li>
                <li>Purchase Time: <%= request.getAttribute("time") %></li>
            </ul>
    </body>
</head>
</html>

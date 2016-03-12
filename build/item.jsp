<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<!DOCTYPE html> 
<html>   
<head> 
<title>Item Search</title>

<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<style type="text/css">
  html { height: 100% } 
  body { height: 100%; margin: 0px; padding: 0px } 
  #map-canvas { height: 100%;}
</style>

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
<script>
  function initialize() {
    var geocoder;
    var map;
    var latlng = new google.maps.LatLng(34.063509,-118.44541);
	geocoder = new google.maps.Geocoder();
	var address = '<%=(String)request.getAttribute("location")%>' ;
	geocoder.geocode( { 'address': address}, function(results, status) {
	  if (status == google.maps.GeocoderStatus.OK) {
		var myOptions = {
		  zoom: 8,
		  center: results[0].geometry.location
		};
		map = new google.maps.Map(document.getElementById('map-canvas'), myOptions);
		if(!address || address == 'null'){
		  map.setZoom(2);
		  map.setCenter(latlng);
		}
		else{
		  var marker = new google.maps.Marker({
		  map: map,
		  position: results[0].geometry.location
		  });
		}
	  }
	  else {			
		var mapOptions = {
		  zoom: 2,
		  center: latlng
		}
		map = new google.maps.Map(document.getElementById('map-canvas'), myOptions);		
	  }
	});
  }
  function codeAddress() {
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
    	map.setCenter(results[0].geometry.location);
    	map.setZoom(8);
    	var marker = new google.maps.Marker({
    		map: map,
    		position: results[0].geometry.location
    	});
      }
    });
  }
google.maps.event.addDomListener(window, 'load', initialize);
</script>

</head> 
<body> 
  <h2><u>Search for Item ID</u></h2>
  <form action="/eBay/item">
    <input name="id" type="text"><br>
    <input type="submit" value="Search" onclick="codeAddress()">
  </form>
  <h2>Results:</h2>
  <p>ItemID:<%= request.getAttribute("itemID")%></p>
  <p>Name: <%= request.getAttribute("name")%></p>
  <p>Categories: <%= request.getAttribute("categories")%></p>
  <p>First Bid: <%= request.getAttribute("firstBid")%></p>
  <p>Number of Bids: <%= request.getAttribute("numberOfBids")%></p>
  <p>Location: <%= request.getAttribute("location")%></p>
  <p>Country: <%= request.getAttribute("country")%></p>
  <p>Started: <%= request.getAttribute("started")%></p>
  <p>Ends: <%= request.getAttribute("ends")%></p>
  <p>Description: <%= request.getAttribute("description")%></p>
  <h3>Seller Details</h3>
  <p>Rating: <%= request.getAttribute("sellerRating")%></p>  
  <p>ID: <%= request.getAttribute("sellerID")%></p>
  <h3>Bid Details</h3>
  <table border="1">
    <tr>
  	<td>Bidder Rating</td>
  	<td>Bidder ID</td>
  	<td>Location</td>
  	<td>Country</td>
  	<td>Time</td>
  	<td>Amount</td>
    </tr>
    <c:forEach begin="0" end="${fn:length(times)}" var="index">
  	<tr>  
  	  <td><c:out value="${ratings[index]}"/></td>
  	  <td><c:out value="${ids[index]}"/></td>
  	  <td><c:out value="${locations[index]}"/></td> 
  	  <td><c:out value="${countries[index]}"/></td>
  	  <td><c:out value="${times[index]}"/></td>
  	  <td><c:out value="${amounts[index]}"/></td>
  	</tr>
    </c:forEach>
  </table>
  <div id="map-canvas"></div> 
</body> 
</html>

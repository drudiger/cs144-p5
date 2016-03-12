package edu.ucla.cs.cs144;


import java.io.IOException;
import java.io.StringReader;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import java.util.*;
import org.w3c.dom.*;


import edu.ucla.cs.cs144.AuctionSearchClient;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}
     
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }

    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }

    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String id = request.getParameter("id");
        if (id == null) id = "";
        request.setAttribute("id", id);
        
        String xml = AuctionSearchClient.getXMLDataForItemId(id);
        
        // Return if item doesn't exist
        if (xml == null || xml.isEmpty()) {
            request.setAttribute("xml", "");
            request.getRequestDispatcher("/item.jsp").forward(request, response);
            return;
        }
        
        try {
        	// Form item element from xml
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder docBuilder = factory.newDocumentBuilder();
            StringReader sReader = new StringReader(xml);
            InputSource iSource = new InputSource(sReader);
            Document doc = docBuilder.parse(iSource);
            Element item = doc.getDocumentElement();

			// Set item attributes
			request.setAttribute("itemID", item.getAttribute("ItemID"));
			request.setAttribute("name", getElementTextByTagNameNR(item, "Name"));
			request.setAttribute("firstBid", getElementTextByTagNameNR(item, "First_Bid"));
			request.setAttribute("numberOfBids", getElementTextByTagNameNR(item, "Number_of_Bids"));
			request.setAttribute("location", getElementTextByTagNameNR(item, "Location"));
			request.setAttribute("country", getElementTextByTagNameNR(item, "Country"));
			request.setAttribute("started", getElementTextByTagNameNR(item, "Started"));
			request.setAttribute("ends", getElementTextByTagNameNR(item, "Ends"));	
			request.setAttribute("description", getElementTextByTagNameNR(item, "Description"));
			
			// Set seller info
			Element seller = getElementByTagNameNR(item, "Seller");
			request.setAttribute("sellerID", seller.getAttribute("UserID"));
			request.setAttribute("sellerRating", seller.getAttribute("Rating"));
	
			// Set item categories
			Element[] itemCategories = getElementsByTagNameNR(item, "Category");
			String categories = "";
			for(int i = 0; i < itemCategories.length; i++) {
				if(i == itemCategories.length - 1)
					categories += getElementText(itemCategories[i]);
				else
					categories += getElementText(itemCategories[i]) + ", ";
			}
			request.setAttribute("categories", categories);
			
			// Set bid info
			Element[] bids = getElementsByTagNameNR(getElementByTagNameNR(item, "Bids"), "Bid");
			
			ArrayList ids = new ArrayList();
			ArrayList times = new ArrayList();
			ArrayList amounts = new ArrayList();
			ArrayList ratings = new ArrayList();
			ArrayList locations = new ArrayList();
			ArrayList countries = new ArrayList();
			
			for (Element bid : bids) {
				Element bidder = getElementByTagNameNR(bid, "Bidder");	
				ids.add(bidder.getAttribute("UserID"));
				times.add(getElementTextByTagNameNR(bid, "Time"));
				amounts.add(getElementTextByTagNameNR(bid, "Amount"));
				ratings.add(bidder.getAttribute("Rating"));
				locations.add(getElementTextByTagNameNR(bidder, "Location"));
				countries.add(getElementTextByTagNameNR(bidder, "Country"));
			}
			
			request.setAttribute("ids", ids);
			request.setAttribute("times", times);
			request.setAttribute("amounts", amounts);
			request.setAttribute("ratings", ratings);
			request.setAttribute("locations", locations);
			request.setAttribute("countries", countries);
		}
        catch (Exception e) {}
        
        request.setAttribute("xml", xml);
        request.getRequestDispatcher("/item.jsp").forward(request, response);     
    }
}

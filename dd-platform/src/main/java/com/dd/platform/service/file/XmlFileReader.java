package com.dd.platform.service.file;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlFileReader {

	public static void main(String argv[]) {
		 
		  try {
	 
			File fXmlFile = new File("C:\\test_folder\\sandbox\\workspaces\\trunk\\dd-platform\\src\\main\\resources\\config\\mp-10293.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
	 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("match");
			System.out.println("-----------------------");
	 
			for (int temp = 0; temp < nList.getLength(); temp++) {
	 
			   Node nNode = nList.item(temp);
			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
			      Element eElement = (Element) nNode;
			      Node firstChild = eElement.getChildNodes().item(0);
			      if (firstChild.getNodeName().equals("moffice_id")) {
				      System.out.println("\nPARSE OFFICE ITEM\n\n");
			      } else {
				      System.out.println("member_id : " + getTagValue("member_id", eElement));
				      System.out.println("person_id : " + getTagValue("person_id", eElement));
				      System.out.println("constituency : " + getTagValue("constituency", eElement));
				      String party = getTagValue("party", eElement);
				      if (party != null) {
				    	  System.out.println("party : " + party);
				      } else {
				    	  System.out.println("party : NULL");			    	  
				      }  
			      }	 
			   }
			}
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	  }
	 
	  private static String getTagValue(String sTag, Element eElement) {
		NodeList elements = eElement.getElementsByTagName(sTag);
		if (elements != null) {
			Node firstItem = elements.item(0);
			if (firstItem != null) {
				NodeList nlList = firstItem.getChildNodes();
				Node nValue = (Node) nlList.item(0);
				if (nValue != null ) {
					return nValue.getNodeValue();
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	  }
	 
	  
}

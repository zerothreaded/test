package com.dd.platform.service.integration;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dd.platform.data.Constituency;
import com.dd.platform.data.MemberOfParliament;
import com.dd.platform.data.Person;
import com.dd.platform.service.UrlLoader;
import com.dd.platform.service.file.TextFileWriter;

public class TheyWorkForYouIntegrationService {

	protected String apiBaseUrl;
	protected String commandgetConstituencies;
	protected String commandGetConstituency;
	protected String commandGetMp;
	protected String baseFolder;
	
	// Dependent Components
	protected UrlLoader urlLoader;
	protected TextFileWriter textFileWriter;
	
	public TheyWorkForYouIntegrationService() {
		
	}

	public String importConstituencyNamesFromWebsite(String
destinationFileName, String key) throws Exception {

		// Import Constituencies data from web site
		String commandUrl = apiBaseUrl + commandgetConstituencies + "&key=" + key;
		System.out.println("commandUrl [" + commandUrl + "]");
		String content = urlLoader.loadUrl(commandUrl);
		System.out.println("Url Data [" + content + "]");
		
		// Save Constituencies data to file
		// Strip the last character from the file to make parsing it easier
		// TODO : replace this later so that content is never mutated
		textFileWriter.write(destinationFileName, content.substring(0,
content.length() - 1));
		
		return content;

	}

	public List<String> importConstituencyNamesFromFile(String fileName) throws Exception {

		LineNumberReader fileReader = new LineNumberReader(new FileReader(fileName));
		String line = new String();
		String done;
		while ((done = fileReader.readLine()) != null) {
			line = done;
			// System.out.println(line);
		}
		
		Integer numberOfConstituencies = 0;
		List<String> constituencyNames = parseConstituencyNamesFromJSContent(line);
		for (String constituencyName : constituencyNames) {
			Constituency constituency = new
Constituency(numberOfConstituencies.toString(), constituencyName);
			// System.out.println("Imported Constituency Name [" + constituency + "]");
			numberOfConstituencies++;			
		}		
		return constituencyNames;
	}
	
	private List<String> parseConstituencyNamesFromJSContent(String line) {
		List<String> results = new ArrayList<String>();
		
		// First Strip the leading '[' and trailing ']'
		line = line.substring(1, line.length() - 1 );
	
		// First split the line into fields
		StringTokenizer fieldTokenizer = new StringTokenizer(line, "}");
		while (fieldTokenizer.hasMoreElements()) {
			// Print fields
			String constituency = (String)fieldTokenizer.nextElement();
			// System.out.println(constituency);			
			// Parsing field for Pattern Below
			// {"name":"Yeovil"} for Yeovil 		
			String name = constituency.substring(2 + constituency.indexOf(":"),
constituency.length() - 1);
			results.add(name);
		}
		return results;
	}

	public String importConstituencyFromWebSite(String constituencyName, String key) throws Exception {

		// Import Constituency data from web site
		constituencyName = constituencyName.replaceAll(" ", "%20");
		String commandUrl = apiBaseUrl + commandGetConstituency + "&key=" + key + "&name=" + constituencyName;
		System.out.println("commandUrl [" + commandUrl + "]");
		String content = this.urlLoader.loadUrl(commandUrl);
		System.out.println("Url Data [" + content + "]");
		
		// Save Constituency data to a file
		String fileName = constituencyName.replaceAll(" ", "-");
		textFileWriter.write(baseFolder + "/" + "getConstituency-" + fileName + "-js.txt", content);

		return content;
	}

	public Map<String, String> importConstituencyFromFile(String constituencyName) throws Exception {
		Map<String, String> data = new TreeMap<String, String>();
		String fileName = baseFolder + "/" + "getConstituency-" + constituencyName + "-js.txt";
		fileName = fileName.replaceAll(" ", "-");
		LineNumberReader fileReader = new LineNumberReader(new FileReader(fileName));
		String content = new String();
		String nextLine;
		while ((nextLine = fileReader.readLine()) != null) {
			content = content + nextLine;
			// System.out.println(content);
			data = parseConstituencyData(content);
		}		
		return data;
	}
	

	public MemberOfParliament importMpDataFromWebsite(String personId, String key) throws Exception {
		MemberOfParliament memberOfParliament = null;

		String commandUrl = apiBaseUrl + commandGetMp + "&key=" + key + "&id=" + personId + "&output=xml";
		System.out.println("commandUrl [" + commandUrl + "]");
		String content = this.urlLoader.loadUrl(commandUrl);
		System.out.println("Url Data [" + content + "]");
		
		// Save MP data to a file
		textFileWriter.write(baseFolder + "/getmps/" + "getMP-" + personId + ".xml", content);
		
		// memberOfParliament = parseMpDetails(content);
		
		return memberOfParliament;
	}

	public MemberOfParliament importMpDataFromFile(String personId) throws Exception {
		MemberOfParliament memberOfParliament = null;
		
//		String fileName = baseFolder + "/" + "getmps/getMP-" + personId + ".xml";
//		LineNumberReader fileReader = new LineNumberReader(new FileReader(fileName));
//		String content = new String();
//		String nextLine;
//		while ((nextLine = fileReader.readLine()) != null) {
//			content = content + nextLine;
//			// System.out.println(content);
//		}		
		// memberOfParliament = parseMpDetails(content);
		memberOfParliament = parseMpDetailsFromXmlFile(personId);
		
		return memberOfParliament;
	}
	
	private MemberOfParliament parseMpDetailsFromXmlFile(String personId) throws Exception {
		MemberOfParliament memberOfParliament = new MemberOfParliament();
		
		String fileName = baseFolder + "/" + "getmps/getMP-" + personId + ".xml";

		File fXmlFile = new File(fileName);
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
		    	  // PARSE MP DETAILS
		    	  String memberId = getTagValue("member_id", eElement);
			      System.out.println("member_id : " + memberId);
			      memberOfParliament.setMemberId(new Long(memberId));
			      
			      memberOfParliament.setPersonId(new Long(personId));
			      
			      String constituencyName = getTagValue("constituency", eElement);
			      System.out.println("constituency : " + constituencyName);
			      memberOfParliament.setConstituency(new Constituency("1", constituencyName));
			      
			      String party = getTagValue("party", eElement);
			      if (party != null) {
			    	  System.out.println("party : " + party);
			      } else {
			    	  System.out.println("party : NULL");			    	  
			      }
			      memberOfParliament.setPartyName(party);
		      }	  
		   }
		}
		return memberOfParliament;
	}
	
	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue != null ) {
			return nValue.getNodeValue();
		} else {
			return null;
		}
	}


	public Map<String, Person> importMpsFromFile(String fileName) throws Exception {
		Map<String, Person> results = new TreeMap<String, Person>();
		fileName = baseFolder + "/getmps/" + fileName;
		LineNumberReader fileReader = new LineNumberReader(new FileReader(fileName));
		String content = new String();
		String nextLine;
		String mpDataBlock = "";
		boolean startBlock = false;

		while ((nextLine = fileReader.readLine()) != null) {
			content = content + nextLine;
			if (nextLine.equals("{")) {
				// Start of Block
				startBlock = true;
			}
			
			if (nextLine.equals("},") || nextLine.equals("}")) {
				// End  of Block
				startBlock = false;
			}
			if (startBlock) {
				// System.out.println("Adding Line to MP block");
				if (!nextLine.equals("{")) {
					// nextLine = nextLine.substring(nextLine.indexOf("\""), nextLine.length());
					mpDataBlock = mpDataBlock + nextLine;
				}
			} else {
				// System.out.println("\n\n READ MP Block\n");
				// System.out.println("[" + mpDataBlock + "]");
				Map<String, String> data = parseMpData(mpDataBlock);
				
				// Build Person
				Person person = new Person();
				person.setFullName(data.get("name"));
				person.setMemberId(new Long(data.get("member_id")));
				person.setPersonId(new Long(data.get("person_id")));
				person.setConstituencyName(data.get("constituency"));
				person.setPartyName(data.get("party"));
				// TODO: Replace this with the data from MP lookup function
				person.setImageUrl("http://www.theyworkforyou.com/images/mps/" + person.getPersonId() + ".jpg");
				
				results.put(person.getConstituencyName(), person);
				
				mpDataBlock = "";
			}
			// System.out.println(nextLine + "" + startBlock);
			// data = parseConstituencyData(content);
		}		
		return results;
	}

	private Map<String, String> parseMpData(String mpDataBlock) {
		Map<String, String> data = new HashMap<String, String>();
		
		char[] content = mpDataBlock.toCharArray();
		// Skip leading 2 spaces + "
		int index = 3;
		
		while(index < mpDataBlock.length()) {
			String name = "";
			boolean foundName = false;
			while (!foundName) {
				name = name + content[index];
				index++;
				if (content[index] == '"') {
					// End of name
					foundName = true;
				}
			}
			name=name.replaceAll("\"", "");
			// Skip over " : "
			index = index + 5;
			// System.out.println("Post Name Skipped to position " + index + " char [" + content[index] + "]");
			
			// Find value
			String value = "";
			boolean foundValue = false;
			while (!foundValue) {
				value = value + content[index];
				index++;
				if (content[index] == '"') {
					// End of name
					foundValue= true;
				}
			}
			value=value.replaceAll("\"", "");
			index = index + 5;
			// System.out.println("Post Value Skipped to position " + index + " char [" + content[index] + "]");
			
			System.out.println("[" + name + "] = [" + value + "]");			
			data.put(name, value);
		}
		return data;
	}

	private Map<String, String> parseConstituencyData(String constituencyData) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		
		// Strip first and last chars { }
		constituencyData = constituencyData.substring(1, constituencyData.length() - 1);

		char[] content = constituencyData.toCharArray();
		int index = 1;
		
		while(index < constituencyData.length()) {
			String name = "";
			boolean foundName = false;
			while (!foundName) {
				name = name + content[index];
				index++;
				if (content[index] == '"') {
					// End of name
					foundName = true;
				}
			}
			name=name.replaceAll("\"", "");
			// Skip over " : "
			index++;
			index++;
	
			// Find value
			String value = "";
			boolean foundValue = false;
			while (!foundValue) {
				value = value + content[index];
				index++;
				if (content[index] == '"') {
					// End of name
					foundValue= true;
				}
			}
			value=value.replaceAll("\"", "");
			index++;
			index++;
			// System.out.println("[" + name + "] = [" + value + "]");			
			data.put(name, value);
		}
		return data;
	}
	
	private MemberOfParliament parseMpDetails(String mpDetails) {
		MemberOfParliament memberOfParliament = new MemberOfParliament();
		
		// Strip first and last chars [ ]
		mpDetails = mpDetails.substring(2, mpDetails.length() - 1);

		System.out.println("TOKENIZING MP DETAILS....");
		StringTokenizer memberTokenizer = new StringTokenizer(mpDetails, "{");
		while (memberTokenizer.hasMoreTokens()) {
			String nextToken = memberTokenizer.nextToken();
			nextToken = nextToken.substring(0, nextToken.length() - 2);
			System.out.println("[" + nextToken + "]");
			System.out.println("TOKENIZING MEMBER DETAILS....");
			StringTokenizer nameValuePairTokenizer = new StringTokenizer(nextToken, ",");
			while (nameValuePairTokenizer.hasMoreTokens()) {
				String nameValuePair = nameValuePairTokenizer.nextToken();
				System.out.print("[" + nameValuePair + "] -->");
				int split = nameValuePair.indexOf(":");
				String name = nameValuePair.substring(0, split);
				String value = nameValuePair.substring(split + 1, nameValuePair.length());
				name = name.substring(1, name.length() - 1);
				
				if (value.startsWith("\"")) {
					value = value.substring(1, value.length() - 1);
				}
				System.out.println("[" + name + "] = [" + value + "]");
			}
		}

		return memberOfParliament;
	}

	public UrlLoader getUrlLoader() {
		return urlLoader;
	}

	public void setUrlLoader(UrlLoader urlLoader) {
		this.urlLoader = urlLoader;
	}

	public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

	public String getCommandgetConstituencies() {
		return commandgetConstituencies;
	}

	public void setCommandgetConstituencies(String commandgetConstituencies) {
		this.commandgetConstituencies = commandgetConstituencies;
	}

	public String getCommandGetConstituency() {
		return commandGetConstituency;
	}

	public void setCommandGetConstituency(String commandGetConstituency) {
		this.commandGetConstituency = commandGetConstituency;
	}

	public String getCommandGetMp() {
		return commandGetMp;
	}

	public void setCommandGetMp(String commandGetMp) {
		this.commandGetMp = commandGetMp;
	}

	public String getBaseFolder() {
		return baseFolder;
	}

	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}
	
	public TextFileWriter getTextFileWriter() {
		return textFileWriter;
	}

	public void setTextFileWriter(TextFileWriter textFileWriter) {
		this.textFileWriter = textFileWriter;
	}
	
}

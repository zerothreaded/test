<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>
<%@page import="com.dd.platform.data.Constituency" %> <%@page import="com.dd.platform.data.Person" %>

<html>
<%
String lookupName = (String) request.getAttribute("FORM.INPUT.LOOKUP_NAME");
if (lookupName == null) {
	lookupName = "";
}
%>
<body>
	<h1>Lookup Routine</h1>
	<table width="320px" height="40px" border="0">
	<tr>
	<form name = "form.cname" method="get" action="showcname.jsp">
	<td>
	<font face="arial" size="-1" color="Gray">Name</font>
	<input type="text" name="lookupName" value="<%=lookupName%>"/> <br/>
	</td>
	<td><input type="submit" value="lookup" /> <br/></td>
	</form>
	</table>
	<h2>DD</h2>
	<font face="arial" size="-1">
	<%
		// Const Names
		ArrayList<String> allConts = (ArrayList<String>)request.getAttribute("CONST.NAMES");
		for (int i = 0; i < allConts.size(); i++) {
			String cName = allConts.get(i);
			// out.println(cName + "<br/>");	
		};
		
		// All Data Items
		Map<String, Constituency> allContData = (Map<String, Constituency>)request.getAttribute("ALL.CONST.DATA");
		//  for (int i = 0; i < allConts.size(); i++) {
		// 	String cName = allConts.get(i);
		// 	Constituency c = (Constituency)allContData.get(cName);
		// 	out.print(c.getId() + "&nbsp" + c.getProperties().get("guardian_id") + "&nbsp" + c.getProperties().get("guardian_name") + "&nbsp");
		// 	out.print(c.getProperties().keySet().toString() + "&nbsp");	
		// 	out.print("<br>");	
		// };

		// Single Data Item	
		Map<String, Constituency> singleConstData = (Map<String, Constituency>)request.getAttribute("SINGLE.CONST.DATA");
		if (singleConstData != null) {
			// Print Const data
			for (String keyName : singleConstData.keySet() ) {
				Constituency c = (Constituency)singleConstData.get(keyName);
				out.print(c.getId() + "&nbsp" + c.getProperties().get("guardian_id") + "&nbsp" + c.getProperties().get("guardian_name") + "&nbsp");
				out.print(c.getProperties().keySet().toString() + "&nbsp");	
				out.print("<br><hr>");	
			}
			// Print MP Data
			Person mp = (Person)request.getAttribute("SINGLE.MP.PERSON");
			out.print(mp.getFullName() + "<br><br>");
			out.print(mp.getConstituencyName() + "<br><br>");
			// out.print(mp.getImageUrl() + "<br>");
			%>
			<img src="<%=mp.getImageUrl() %>" >
			<%
		}
	%>
	</font>
	<b>Replace Value</b>
	<hr />
</body>
</html>

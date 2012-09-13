<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>

<html>
<body>
	<h1>/view/authenticate.jsp file</h1>

	<h2>Authenticate</h2>
	<%
		String content = (String)request.getAttribute("AUTH_DATE");
		out.println(content);
	%>
	<hr/>
</body>
</html>

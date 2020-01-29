<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script>
	alert("Invalid details!!")
</script>
</head>
<body>
	<%
		boolean sess=false;
		if(session.getAttribute("User")==null)
		{
			sess=true;
		}
		if(sess==false)
		{
	%>
		<jsp:forward page="index.jsp"></jsp:forward>
	<% 		
		}
			
	%>
	<jsp:include page="home.jsp"></jsp:include>
</body>
</html>
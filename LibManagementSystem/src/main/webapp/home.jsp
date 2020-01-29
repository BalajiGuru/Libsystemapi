<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<style>
#click,#click1 {
	cursor: pointer;
}
#content{
	
	position:absolute;
	left:200px;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {

		$("#registerform").hide();

		$("#click").click(function() {

			$("#registerform").show();
			$("#click").hide();
			$("#loginform").hide();
		})

		$("#register").click(function() {

			reg();

		})

		$("#click1").click(function(){
			
			$("#loginform").show();
			$("#registerform").hide();
			$("#click").show();
			
				
		})
	})
</script>

</head>
<body style="background-color: #ffff66">
	
	<%
		response.setHeader("Cache-Control", "no-store");
	%>
	<%
		boolean sess = false;
		if (session.getAttribute("User") != null) {
			sess = true;
		}
		if (sess == true) {
	%>
	<jsp:forward page="index.jsp"></jsp:forward>
	<%
		}
	%>
	
	<center><h1 style="color: blue">Balaji's Library</h1></center>
	
	<div id="content">
		<div id="loginform">
		<h3 style="color: blue">Please Log in if you are a current user</h3>
		
		<form action="Verify" method="Post" style="color: #cc00ff">
			<table>
				<tr>
					<td>Username :</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input type="password" name="password"></td>
				</tr>	
				<tr>
				<td><input type="Submit" value="Login"></td>
			</table>
		</form>
			
		<p id="click" style="color: #3333ff">Click here if you are a new
			user</p>
		</div>

		<div id="registerform">
			<h2 style="color: blue">Register</h2>

			<form id="form1" method="Post" enctype="multipart/form-data">
				<table style="color:#cc00ff">
					<tr>
						<td>UserName :</td>
						<td><input type="text" id="username" name="username"></td>
					</tr>
					<tr>
						<td>Password :</td>
						<td><input type="password" id="password" name="password"></td>
					</tr>
					<tr>
						<td>Upload Profile Picture :</td>
						<td><input type="file" id="img1" accept="images/*" name="dp"></td>

					</tr>
					<tr>
						<td><input type="button" value="Register" id="register"></td>
					</tr>
					
				</table>
			<p id="click1" style="color: #3333ff">Click to login</p>
			
			</form>
		</div>
	</div>
</body>
</html>
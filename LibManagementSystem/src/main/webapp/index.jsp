<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Cache-Control" content="no-store" />

<title>Books Details</title>
<style>
#profile {
	float: right;
}

#searchbooks {
	position: relative;
	top: 30px;
}

#content {
	position: absolute;
	left: 200px;
}
</style>

<script src="bookdetails.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {

		$("#bookform").hide();

		display();

		$("#addbook").click(function() {

			add();
			$("#bookform").hide();
			$("#addbookform").show();

		})
		$("#search").click(function() {

			search();

		});

		$("#addbookform").click(function() {

			$("#bookform").show();
			$("#addbookform").hide();
		})

	});
</script>
</head>
<body style="background: #ffff66">

	<center>
		<h1 style="color: blue">Balaji's Library</h1>
	</center>
	<div id="profile">
		<img src="data:image/jpeg;base64,${userImage}" height="120px"
			width="120px" />

		<%
			boolean sess = false;
			if (session.getAttribute("User") == null) {
				sess = true;

			}
			if (sess == true) {
		%>
		<jsp:forward page="/"></jsp:forward>
		<%
			}
		%>




		<h4 style="color: red">Hi, ${User}</h4>

		<form action="/logout">
			<input type="submit" value="Log Out">
		</form>

	</div>
	<div id="content">
		<h2 style="color: blue">Book Details</h2>
		<h3 style="color: blue">Search Books</h3>

		<form>
			<table>
				<tr>
					<td>Search By :</td>
					<td><select id="searchby">
							<option value="bname">BookName</option>
							<option value="author">Author</option>
							<option value="publisher">Publisher</option>
							<option value="Isbn">Isbn</option>
							<option value="genre">Genre</option>
					</select></td>
					<td><input type="text" id="searchtext"></td>
					<td><input type="button" value="Search" id="search"></td>
				</tr>

			</table>
		</form>
		<br>
		<table border="1" style="color: #663300">
			<thead>
				<th>Book Name</th>
				<th>Author</th>
				<th>Pages</th>
				<th>Publisher</th>
				<th>ISBN</th>
				<th>Genre</th>
			</thead>
			<tbody id="tablebody">

			</tbody>
		</table>
		<div id="searchbooks">
			<input type="button" id="addbookform" value="Add Book">
			<form id="bookform">
				<h3 style="color: blue">Add Book Form</h3>
				<table style="color:#cc00ff">

					<tr>
						<td>Book Name :</td>
						<td><input type="text" id="bname"></td>
					</tr>
					<tr>
						<td>Author :</td>
						<td><input type="text" id="author"></td>
					</tr>
					<tr>
						<td>Pages :</td>
						<td><input type="text" id="pages"></td>
					</tr>
					<tr>
						<td>Publisher :</td>
						<td><input type="text" id="publisher"></td>
					</tr>
					<tr>
						<td>ISBN :</td>
						<td><input type="text" id="isbn"></td>
					</tr>
					<tr>
						<td>Genre :</td>
						<td><input type="text" id="genre"></td>
					</tr>
					<tr>
						<td><input type="button" id="addbook" value="Add Book"></td>
					</tr>
				</table>
			</form>


		</div>
	</div>


</body>
</html>
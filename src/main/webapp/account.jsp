<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="model.User"%>
<!DOCTYPE html>
<html>
<head>
<title>Account</title>
<!-- Bootstrap CSS -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<%
	// Check if user is logged in
	User user = (User) session.getAttribute("user");
	if (user == null) {
		// Redirect to login page if not logged in
		response.sendRedirect("login.jsp");
		return; // Stop further execution of the page
	}
	%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container mt-5">
			<a class="navbar-brand" href="#">User</a>

			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link"
					href="account.jsp">User Profile <span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"></li>
			</ul>

			<form action="LogoutServlet" method="post">
				<button type="submit" class="btn btn-primary">Logout</button>
			</form>

		</div>
	</nav>
	<div class="container">
		<h1 class="mt-5">Account Information</h1>
		<c:if test="${not empty user}">
			<div class="card mt-3">
				<div class="card-body">
					<p class="card-text">Username: ${user.username}</p>
					<p class="card-text">Email: ${user.email}</p>
					<p class="card-text">Registration Date:
						${user.registrationDate}</p>
					<p class="card-text">Expiry Date: ${user.expiryDate}</p>
				</div>
			</div>
		</c:if>
	</div>
</body>
</html>

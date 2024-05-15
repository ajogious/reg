<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="model.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>admin profile</title>
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
			<a class="navbar-brand" href="#">Admin Panel</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item active"><a class="nav-link"
						href="admin_profile.jsp">Admin Profile <span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item">
						<form action="ViewAllUsersServlet" method="get">
							<button type="submit" class="border-0 bg-transparent nav-link">Users</button>
						</form>
					</li>
					<li class="nav-item">
						<form action="ViewActiveAccountsServlet" method="get">
							<button type="submit" class="border-0 bg-transparent nav-link">Active
								Accounts</button>
						</form>
					</li>
					<li class="nav-item">
						<form action="ViewExpiredAccountsServlet" method="get">
							<button type="submit" class="border-0 bg-transparent nav-link">Expired
								Accounts</button>
						</form>
					</li>
					<li class="nav-item">
						<form action="LogoutServlet" method="post">
							<button type="submit" class="btn btn-primary">Logout</button>
						</form>
					</li>
				</ul>

				<form class="form-inline my-2 my-lg-0">
					<input class="form-control mr-sm-2" id="searchQuery" type="search"
						placeholder="Search" aria-label="Search">
				</form>

			</div>
		</div>
	</nav>

	<div class="container">
		<h1 class="mt-5">Admin Information</h1>
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

	<script>
		// Function to send AJAX request for search
		function searchUsers() {
			var query = document.getElementById("searchQuery").value.trim();
			if (query !== "") {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200) {
						document.getElementById("usersTable").innerHTML = this.responseText;
					}
				};
				xhttp.open("GET", "SearchUserServlet?query=" + query, true);
				xhttp.send();
			}
		}

		// Call the searchUsers function whenever the input field value changes
		document.getElementById("searchQuery").addEventListener("input",
				searchUsers);
	</script>

</body>
</html>
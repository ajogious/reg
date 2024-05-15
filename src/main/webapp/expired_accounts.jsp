<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="model.User"%>
<!DOCTYPE html>
<html>
<head>
<title>Expired Accounts</title>
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
					<input class="form-control mr-sm-2" type="search"
						placeholder="Search" aria-label="Search">
				</form>

			</div>
		</div>
	</nav>

	<div class="container mt-5">
		<h1>Expired Accounts</h1>
		<c:if test="${empty expiredUsers}">
			<p>No expired accounts found.</p>
		</c:if>
		<c:if test="${not empty expiredUsers}">
			<table class="table table-bordered">
				<thead class="thead-dark">
					<tr>
						<th>Username</th>
						<th>Email</th>
						<th>Expiry Date</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${expiredUsers}">
						<tr>
							<td>${user.username}</td>
							<td>${user.email}</td>
							<td>${user.expiryDate}</td>
							<td>
								<form action="ReactivateUserServlet" method="post">
									<input type="hidden" name="id" value="${user.id}"> <input
										type="number" name="days" placeholder="Days" required>
									<button type="submit" class="btn btn-primary">Reactivate</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>

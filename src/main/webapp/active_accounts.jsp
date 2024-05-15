<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Active Accounts</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
        <h1>Active Accounts</h1>
        <table class="table table-bordered mt-4">
            <thead class="thead-dark">
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Registration Date</th>
                    <th>Expiry Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${activeUsers}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.registrationDate}</td>
                        <td>${user.expiryDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

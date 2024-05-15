<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="model.User"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Panel</title>
<!-- Bootstrap CSS -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<%
	// Check if user is logged in
	User u = (User) session.getAttribute("user");
	if (u == null) {
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

	<div class="container mt-5">
		<%-- Display Notification Message --%>
		<%
		String notification = (String) request.getSession().getAttribute("notification");
		if (notification != null && !notification.isEmpty()) {
		%>
		<div class="alert alert-success" role="alert">
			<%=notification%>
		</div>
		<%
		request.getSession().removeAttribute("notification"); // Clear notification after displaying
		}
		%>

		<h1 class="mb-4">All Users</h1>
		<table class="table">
			<thead class="thead-light">
				<tr>
					<th>Username</th>
					<th>Email</th>
					<th>Expiry Date</th>
					<th>Role</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<User> users = (List<User>) request.getAttribute("users");
				if (users != null && !users.isEmpty()) {
					for (User user : users) {
				%>
				<tr>
					<td><%=user.getUsername()%></td>
					<td><%=user.getEmail()%></td>
					<td><%=user.getExpiryDate()%></td>
					<td><%=user.getRole()%></td>
					<td>
						<form action="DeleteUserServlet" method="post">
							<input type="hidden" name="id" value="<%=user.getId()%>">
							<button type="submit" class="btn btn-danger btn-sm">Delete</button>
						</form>
					</td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="5">No users found</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<%-- Pagination --%>
		<%
		Integer currentPageObj = (Integer) request.getAttribute("currentPage");
		Integer noOfPagesObj = (Integer) request.getAttribute("noOfPages");
		int currentPage = currentPageObj != null ? currentPageObj.intValue() : 1;
		int noOfPages = noOfPagesObj != null ? noOfPagesObj.intValue() : 1;
		%>
		<nav aria-label="Page navigation example">
			<ul class="pagination">
				<%
				if (currentPage > 1) {
				%>
				<li class="page-item"><a class="page-link"
					href="ViewAllUsersServlet?page=<%=currentPage - 1%>"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						<span class="sr-only">Previous</span>
				</a></li>
				<%
				}
				%>
				<%
				for (int i = 1; i <= noOfPages; i++) {
				%>
				<li class="page-item <%=currentPage == i ? "active" : ""%>"><a
					class="page-link" href="ViewAllUsersServlet?page=<%=i%>"><%=i%></a>
				</li>
				<%
				}
				%>
				<%
				if (currentPage < noOfPages) {
				%>
				<li class="page-item"><a class="page-link"
					href="ViewAllUsersServlet?page=<%=currentPage + 1%>"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
						class="sr-only">Next</span>
				</a></li>
				<%
				}
				%>
			</ul>
		</nav>
	</div>
</body>
</html>
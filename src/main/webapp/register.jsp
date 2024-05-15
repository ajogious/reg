<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error-message {
            color: red;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Register</h1>
        <%-- Error Messages --%>
        <%
            String error = request.getParameter("error");
            if ("duplicate".equals(error)) {
        %>
                <p class="error-message">Username or email already exists. Please choose a different one.</p>
        <%
            } else if ("unknown".equals(error)) {
        %>
                <p class="error-message">An unknown error occurred. Please try again later.</p>
        <%
            }
        %>
        <form action="RegisterServlet" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Register</button>
        </form>
        <p class="mt-3">Already have an account? <a href="login.jsp">Log in here</a>.</p>
    </div>
</body>
</html>

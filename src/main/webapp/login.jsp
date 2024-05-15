<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Login</h1>
        <%-- Error Messages --%>
        <%
        String error = request.getParameter("error");
        if ("invalid".equals(error)) {
        %>
        <p class="text-danger">Invalid email or password. Please try again.</p>
        <%
        }
        %>
        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label for="email">Email:</label> 
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label> 
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>
        <p class="mt-3">No account yet? <a href="register.jsp">Register here</a>.</p>
    </div>
</body>
</html>

package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.getUserByEmailAndPassword(email, password);
            if (user != null) {
                // User authenticated successfully
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Get user's role
                String userRole = userDAO.getUserRole(email);
                session.setAttribute("userRole", userRole);

                // Redirect based on user's role
                if ("admin".equals(userRole)) {
                    // If user is an admin, redirect to admin dashboard
                    response.sendRedirect("admin_profile.jsp");
                } else {
                    // If user is not an admin, redirect to user dashboard
                    response.sendRedirect("account.jsp");
                }
            } else {
                // Invalid credentials, redirect to login page with error message
                response.sendRedirect("login.jsp?error=invalid");
            }  
        } catch (SQLException e) {
            response.sendRedirect("error.jsp");
        }
    }
}

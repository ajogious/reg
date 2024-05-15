package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        UserDAO userDAO = new UserDAO();
        try {
            userDAO.registerUser(user);
            response.sendRedirect("login.jsp");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // MySQL error code for duplicate key violation
                response.sendRedirect("register.jsp?error=duplicate");
            } else {
                e.printStackTrace();
                response.sendRedirect("register.jsp?error=unknown");
            }
        }
    }
}

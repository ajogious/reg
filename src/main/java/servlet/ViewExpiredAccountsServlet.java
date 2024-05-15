package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/ViewExpiredAccountsServlet")
public class ViewExpiredAccountsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve expired accounts from the database
            List<User> expiredUsers = new UserDAO().getExpiredUsers();
            
            // Set expired users as an attribute in request scope
            request.setAttribute("expiredUsers", expiredUsers);
            
            // Forward the request to the expired accounts view page
            request.getRequestDispatcher("expired_accounts.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

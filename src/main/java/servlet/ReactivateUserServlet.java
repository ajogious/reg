package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ReactivateUserServlet")
public class ReactivateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        int days = Integer.parseInt(request.getParameter("days"));

        UserDAO userDAO = new UserDAO();
        try {
            userDAO.reactivateUser(userId, days);
            response.sendRedirect("ViewExpiredAccountsServlet");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


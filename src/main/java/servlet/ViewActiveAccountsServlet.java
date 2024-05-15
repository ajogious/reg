package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/ViewActiveAccountsServlet")
public class ViewActiveAccountsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage = 1;
        int recordsPerPage = 5; // You can adjust this value as needed

        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        try {
            UserDAO userDAO = new UserDAO();
            int offset = (currentPage - 1) * recordsPerPage;
            List<User> activeUsers = userDAO.getActiveUsers(offset, recordsPerPage);
            int noOfRecords = userDAO.getNoOfActiveUsers();

            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("activeUsers", activeUsers);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("noOfPages", noOfPages);
            RequestDispatcher dispatcher = request.getRequestDispatcher("active_accounts.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

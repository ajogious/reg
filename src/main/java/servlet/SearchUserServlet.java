package servlet;

import java.io.IOException;
import java.util.List;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/SearchUserServlet")
public class SearchUserServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");

        UserDAO userDAO = new UserDAO();

        // Implement your search logic here, focusing on the users table
        List<User> searchResults = userDAO.searchUsersByUsername(query);

        // Prepare the HTML response
        StringBuilder htmlResponse = new StringBuilder();
        for (User user : searchResults) {
            htmlResponse.append("<tr>");
            htmlResponse.append("<td>").append(user.getUsername()).append("</td>");
            // Add more user details as needed
            htmlResponse.append("</tr>");
        }

        // Set the response content type
        response.setContentType("text/html");
        // Write the HTML response back to the client
        response.getWriter().write(htmlResponse.toString());
    }
}


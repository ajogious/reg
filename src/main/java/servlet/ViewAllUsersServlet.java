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

@WebServlet("/ViewAllUsersServlet")
public class ViewAllUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 10;
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        try {
        UserDAO userDAO = new UserDAO();
        userDAO.getAllUsers(page, recordsPerPage);
       
		List<User> users = userDAO.getAllUsers(page, recordsPerPage);
	
        int noOfRecords = 0;
            users = userDAO.getAllUsers((page - 1) * recordsPerPage, recordsPerPage);
            noOfRecords = userDAO.getNoOfRecords();
  
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("users", users);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
    }
}



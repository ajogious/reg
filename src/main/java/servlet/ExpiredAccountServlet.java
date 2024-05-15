package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ExpiredAccountServlet")
public class ExpiredAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/account_management", "root", "password");

            String query = "SELECT * FROM expired_users WHERE expiry_date < NOW()";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("id");
                String moveQuery = "INSERT INTO expired_users (user_id, expiry_date) VALUES (?, ?)";
                PreparedStatement movePs = con.prepareStatement(moveQuery);
                movePs.setInt(1, userId);
                movePs.setTimestamp(2, rs.getTimestamp("expiry_date"));
                movePs.executeUpdate();

                String deleteQuery = "DELETE FROM users WHERE id = ?";
                PreparedStatement deletePs = con.prepareStatement(deleteQuery);
                deletePs.setInt(1, userId);
                deletePs.executeUpdate();
            }

            response.sendRedirect("expired_accounts.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

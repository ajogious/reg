package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.DBUtil;

public class UserDAO {
	public void registerUser(User user) throws SQLException {
		String query = "INSERT INTO users (username, email, password, expiry_date) VALUES (?, ?, ?, DATE_ADD(NOW(), INTERVAL 30 DAY))";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
		}
	}

	public User getUserByEmailAndPassword(String email, String password) throws SQLException {
		String query = "SELECT * FROM users WHERE email = ? AND password = ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, email);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setRegistrationDate(rs.getTimestamp("registration_date"));
					user.setExpiryDate(rs.getTimestamp("expiry_date"));
					return user;
				}
			}
		}
		return null;
	}

	public List<User> getAllUsers(int offset, int noOfRecords) throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT SQL_CALC_FOUND_ROWS * FROM users LIMIT ?, ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, offset);
			ps.setInt(2, noOfRecords);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setRegistrationDate(rs.getTimestamp("registration_date"));
					user.setRole(rs.getString("role"));
					user.setExpiryDate(rs.getTimestamp("expiry_date"));
					users.add(user);
				}
			}
		}
		return users;
	}

	public int getNoOfRecords() throws SQLException {
		String query = "SELECT FOUND_ROWS()";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	public void deleteUser(int userId) throws SQLException {
		String query = "DELETE FROM users WHERE id = ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, userId);
			ps.executeUpdate();
		}
	}

	public List<User> getExpiredUsers() throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users WHERE expiry_date < NOW()";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRegistrationDate(rs.getTimestamp("registration_date"));
				user.setExpiryDate(rs.getTimestamp("expiry_date"));
				users.add(user);
			}
		}
		return users;
	}

	public void moveToExpiredUsers(int userId) throws SQLException {
		String query = "INSERT INTO expired_users (user_id, expiry_date) SELECT id, expiry_date FROM users WHERE id = ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, userId);
			ps.executeUpdate();

			String deleteQuery = "DELETE FROM users WHERE id = ?";
			try (PreparedStatement deletePs = con.prepareStatement(deleteQuery)) {
				deletePs.setInt(1, userId);
				deletePs.executeUpdate();
			}
		}
	}

	public void reactivateUser(int userId, int days) throws SQLException {
		String query = "UPDATE users SET expiry_date = DATE_ADD(NOW(), INTERVAL ? DAY) WHERE id = ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, days);
			ps.setInt(2, userId);
			ps.executeUpdate();
		}
	}

	public String getUserRole(String email) throws SQLException {
		String role = null;
		String query = "SELECT role FROM users WHERE email = ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					role = rs.getString("role");
				}
			}
		}
		return role;
	}

	public void updateExpiryDate(int userId, Date newExpiryDate) throws SQLException {
		String query = "UPDATE users SET expiry_date = ? WHERE id = ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setTimestamp(1, new java.sql.Timestamp(newExpiryDate.getTime()));
			ps.setInt(2, userId);
			ps.executeUpdate();
		}
	}

	public List<User> getApproachingExpiryUsers(int daysBeforeExpiry) throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users WHERE expiry_date <= DATE_ADD(NOW(), INTERVAL ? DAY)";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, daysBeforeExpiry);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setRegistrationDate(rs.getTimestamp("registration_date"));
					user.setExpiryDate(rs.getTimestamp("expiry_date"));
					users.add(user);
				}
			}
		}
		return users;
	}

	public List<User> getActiveUsers(int offset, int noOfRecords) throws SQLException {
		List<User> activeUsers = new ArrayList<>();
		String query = "SELECT * FROM users WHERE expiry_date > NOW() LIMIT ?, ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, offset);
			ps.setInt(2, noOfRecords);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setRegistrationDate(rs.getTimestamp("registration_date"));
					user.setExpiryDate(rs.getTimestamp("expiry_date"));
					activeUsers.add(user);
				}
			}
		}
		return activeUsers;
	}

	public int getNoOfActiveUsers() throws SQLException {
		String query = "SELECT COUNT(*) FROM users WHERE expiry_date >= NOW()";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	public static List<User> searchUsersByUsername(String query) {
		List<User> users = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM users WHERE username LIKE ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + query + "%");
			rs = stmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setRegistrationDate(rs.getDate("registration_date"));
				user.setExpiryDate(rs.getDate("expiry_date"));
				user.setRole(rs.getString("role"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

}

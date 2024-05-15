package mail_notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import model.User;
import util.DBUtil;

public class NotificationTask extends TimerTask {
    @Override
    public void run() {
        try (Connection con = DBUtil.getConnection()) {
            // Get users whose account expiry date is approaching (e.g., 10 days before expiry)
            List<User> users = getUsersWithApproachingExpiryDate(con, 10);
            for (User user : users) {
                sendEmail(user.getEmail(), "Account Expiry Notification", "Your account will expire soon. Please take necessary action.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<User> getUsersWithApproachingExpiryDate(Connection con, int daysBeforeExpiry) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE expiry_date <= DATE_ADD(NOW(), INTERVAL ? DAY)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
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

    private void sendEmail(String to, String subject, String body) {
        String from = "your-email@example.com";
        String host = "smtp.example.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
           
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Notification email sent successfully to: " + to);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

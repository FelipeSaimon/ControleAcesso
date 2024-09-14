/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Erik
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Notification;
import model.User;

public class NotificationDAO {
    private Connection connection;
    private UserDAO userDAO;

    public NotificationDAO(Connection connection, UserDAO userDAO) {
        this.connection = connection;
        this.userDAO = userDAO;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS notifications (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "message TEXT NOT NULL," +
                     "sender_id INTEGER NOT NULL," +
                     "is_read BOOLEAN NOT NULL," +
                     "send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                     "FOREIGN KEY (sender_id) REFERENCES users(id))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "CREATE TABLE IF NOT EXISTS notification_recipients (" +
              "notification_id INTEGER," +
              "recipient_id INTEGER," +
              "PRIMARY KEY (notification_id, recipient_id)," +
              "FOREIGN KEY (notification_id) REFERENCES notifications(id)," +
              "FOREIGN KEY (recipient_id) REFERENCES users(id))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Notification notification) {
        String sql = "INSERT INTO notifications (message, sender_id, is_read) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, notification.getMessage());
            pstmt.setInt(2, notification.getSender().getId());
            pstmt.setBoolean(3, notification.isRead());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setId(generatedKeys.getInt(1));
                }
            }

            for (User recipient : notification.getRecipients()) {
                addRecipient(notification.getId(), recipient.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addRecipient(int notificationId, int recipientId) {
        String sql = "INSERT INTO notification_recipients (notification_id, recipient_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, notificationId);
            pstmt.setInt(2, recipientId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Notification read(int id) {
        String sql = "SELECT * FROM notifications WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractNotificationFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Notification notification) {
        String sql = "UPDATE notifications SET message = ?, sender_id = ?, is_read = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, notification.getMessage());
            pstmt.setInt(2, notification.getSender().getId());
            pstmt.setBoolean(3, notification.isRead());
            pstmt.setInt(4, notification.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Notification notification) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, notification.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getNotificationsForUser(User user) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT n.* FROM notifications n " +
                     "JOIN notification_recipients nr ON n.id = nr.notification_id " +
                     "WHERE nr.recipient_id = ? ORDER BY n.send_date DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                notifications.add(extractNotificationFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public int getUnreadNotificationsCount(User user) {
        String sql = "SELECT COUNT(*) FROM notifications n " +
                     "JOIN notification_recipients nr ON n.id = nr.notification_id " +
                     "WHERE nr.recipient_id = ? AND n.is_read = 0";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Notification extractNotificationFromResultSet(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getInt("id"));
        notification.setMessage(rs.getString("message"));
        notification.setSender(userDAO.read(rs.getInt("sender_id")));
        notification.setRead(rs.getBoolean("is_read"));
        notification.setSendDate(rs.getTimestamp("send_date"));
        notification.setRecipients(getRecipientsForNotification(notification.getId()));
        return notification;
    }

    private List<User> getRecipientsForNotification(int notificationId) {
        List<User> recipients = new ArrayList<>();
        String sql = "SELECT recipient_id FROM notification_recipients WHERE notification_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, notificationId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                recipients.add(userDAO.read(rs.getInt("recipient_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipients;
    }
}

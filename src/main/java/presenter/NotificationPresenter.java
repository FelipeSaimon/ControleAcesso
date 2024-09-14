/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

/**
 *
 * @author Erik
 */
import dao.NotificationDAO;
import dao.UserDAO;
import java.util.List;
import model.Notification;
import model.User;
import view.NotificationView;

public class NotificationPresenter {
    private NotificationView view;
    private NotificationDAO notificationDAO;
    private UserDAO userDAO;
    private User currentUser;

    public NotificationPresenter(NotificationView view, NotificationDAO notificationDAO, UserDAO userDAO) {
        this.view = view;
        this.notificationDAO = notificationDAO;
        this.userDAO = userDAO;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void loadNotifications() {
        List<Notification> notifications = notificationDAO.getNotificationsForUser(currentUser);
        view.showNotificationList(notifications);
    }

    public void sendNotification(String message, List<User> recipients) {
        if (!currentUser.isAdmin()) {
            view.showError("Only administrators can send notifications.");
            return;
        }

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setSender(currentUser);
        notification.setRecipients(recipients);

        notificationDAO.create(notification);
        currentUser.incrementSentNotificationsCount();
        userDAO.update(currentUser);

        for (User recipient : recipients) {
            // You might want to implement a method to increment unread notifications count
            userDAO.update(recipient);
        }

        view.showSuccess("Notification sent successfully.");
        loadNotifications();
    }

    public void markAsRead(Notification notification) {
        if (!notification.isRead()) {
            notification.setRead(true);
            notificationDAO.update(notification);
            currentUser.incrementReadNotificationsCount();
            userDAO.update(currentUser);
            view.showSuccess("Notification marked as read.");
            loadNotifications();
        }
    }

    public void deleteNotification(Notification notification) {
        notificationDAO.delete(notification);
        view.showSuccess("Notification deleted successfully.");
        loadNotifications();
    }

    public List<User> getAllUsers() {
        return userDAO.list();
    }

    public void showSendNotificationForm() {
        List<User> allUsers = getAllUsers();
        view.showSendNotificationForm(allUsers);
    }

    public int getUnreadNotificationsCount() {
        return notificationDAO.getUnreadNotificationsCount(currentUser);
    }
}

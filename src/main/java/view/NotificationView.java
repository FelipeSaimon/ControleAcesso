/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Erik
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Notification;
import model.User;
import presenter.NotificationPresenter;

public class NotificationView extends JFrame {
    private JList<Notification> notificationList;
    private DefaultListModel<Notification> listModel;
    private JButton readButton;
    private JButton deleteButton;
    private JButton sendButton;
    private NotificationPresenter presenter;

    public NotificationView() {
        setTitle("Notifications");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        notificationList = new JList<>(listModel);
        add(new JScrollPane(notificationList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        readButton = new JButton("Mark as Read");
        deleteButton = new JButton("Delete");
        sendButton = new JButton("Send New");

        buttonPanel.add(readButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sendButton);

        add(buttonPanel, BorderLayout.SOUTH);

        readButton.addActionListener(e -> presenter.markAsRead(notificationList.getSelectedValue()));
        deleteButton.addActionListener(e -> presenter.deleteNotification(notificationList.getSelectedValue()));
        sendButton.addActionListener(e -> presenter.showSendNotificationForm());
    }

    public void setPresenter(NotificationPresenter presenter) {
        this.presenter = presenter;
    }

    public void showNotificationList(List<Notification> notifications) {
        listModel.clear();
        for (Notification notification : notifications) {
            listModel.addElement(notification);
        }
    }

    public void showSendNotificationForm(List<User> allUsers) {
        JDialog dialog = new JDialog(this, "Send Notification", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);

        JTextArea messageArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(messageArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JList<User> userList = new JList<>(allUsers.toArray(new User[0]));
        userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dialog.add(new JScrollPane(userList), BorderLayout.EAST);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            String message = messageArea.getText();
            List<User> recipients = userList.getSelectedValuesList();
            if (!message.isEmpty() && !recipients.isEmpty()) {
                presenter.sendNotification(message, recipients);
                dialog.dispose();
            } else {
                showError("Please enter a message and select at least one recipient.");
            }
        });
        dialog.add(sendButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

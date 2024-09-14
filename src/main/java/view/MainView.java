package view;

import javax.swing.*;
import java.awt.*;
import model.User;
import presenter.NotificationPresenter;
import presenter.UserManagementPresenter;
import presenter.ConfigurationPresenter;

public class MainView extends JFrame {
    private JTabbedPane tabbedPane;
    private UserManagementView userManagementView;
    private NotificationView notificationView;
    private ConfigurationView configurationView;
    private User currentUser;

    private UserManagementPresenter userManagementPresenter;
    private NotificationPresenter notificationPresenter;
    private ConfigurationPresenter configurationPresenter;

    public MainView(UserManagementView userManagementView, NotificationView notificationView, ConfigurationView configurationView,
                    UserManagementPresenter userManagementPresenter, NotificationPresenter notificationPresenter, ConfigurationPresenter configurationPresenter) {
        this.userManagementView = userManagementView;
        this.notificationView = notificationView;
        this.configurationView = configurationView;
        this.userManagementPresenter = userManagementPresenter;
        this.notificationPresenter = notificationPresenter;
        this.configurationPresenter = configurationPresenter;

        setTitle("Sistema de Gestão de Usuários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        add(tabbedPane);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void showMainView() {
        tabbedPane.removeAll();  // Clear existing tabs

        if (currentUser.isAdmin()) {
            tabbedPane.addTab("Gestão de usuarios", userManagementView.getContentPane());
        }
        tabbedPane.addTab("Notificações", notificationView.getContentPane());
        tabbedPane.addTab("Configuração", configurationView.getContentPane());

        notificationPresenter.setCurrentUser(currentUser);
        notificationPresenter.loadNotifications();

        setVisible(true);
    }
}
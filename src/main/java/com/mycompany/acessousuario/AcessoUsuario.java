package com.mycompany.acessousuario;

import dao.UserDAO;
import dao.NotificationDAO;
import view.LoginView;
import view.UserManagementView;
import view.NotificationView;
import view.ConfigurationView;
import view.MainView;
import log.LogManager;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import presenter.ConfigurationPresenter;
import presenter.LoginPresenter;
import presenter.NotificationPresenter;
import presenter.UserManagementPresenter;

public class AcessoUsuario {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializa a conexão com o banco de dados
                Connection connection = DriverManager.getConnection("jdbc:sqlite:users.db");

                // Inicializa os DAOs
                UserDAO userDAO = new UserDAO();
                NotificationDAO notificationDAO = new NotificationDAO(connection, userDAO);

                // Inicializa o LogManager
                LogManager logManager = new LogManager();

                // Cria as views
                LoginView loginView = new LoginView();
                UserManagementView userManagementView = new UserManagementView();
                NotificationView notificationView = new NotificationView();
                ConfigurationView configurationView = new ConfigurationView();

                // Cria os presenters
                UserManagementPresenter userManagementPresenter = new UserManagementPresenter(userManagementView, userDAO);
                NotificationPresenter notificationPresenter = new NotificationPresenter(notificationView, notificationDAO, userDAO);
                ConfigurationPresenter configurationPresenter = new ConfigurationPresenter(configurationView, logManager);

                // Cria a MainView
                MainView mainView = new MainView(userManagementView, notificationView, configurationView,
                                                 userManagementPresenter, notificationPresenter, configurationPresenter);

                // Cria e configura o LoginPresenter
                LoginPresenter loginPresenter = new LoginPresenter(loginView, userDAO, mainView);

                // Configura as views com seus respectivos presenters
                loginView.setPresenter(loginPresenter);
                userManagementView.setPresenter(userManagementPresenter);
                notificationView.setPresenter(notificationPresenter);
                configurationView.setPresenter(configurationPresenter);

                // Exibe a janela de login
                loginView.setVisible(true);

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
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
import presenter.LoginPresenter;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private LoginPresenter presenter;

    public LoginView() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Registrar");

        add(new JLabel("Usuario:"));
        add(usernameField);
        add(new JLabel("Senha:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());
    }

    public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        presenter.login(username, password);
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        presenter.register(username, password);
    }

    public void showLoginForm() {
        setVisible(true);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import dao.UserDAO;
import model.User;
import view.LoginView;
import view.MainView;

/**
 *
 * @author Erik
 */
public class LoginPresenter {
    private LoginView view;
    private UserDAO userDAO;
    private MainView mainView;

    public LoginPresenter(LoginView view, UserDAO userDAO, MainView mainView) {
        this.view = view;
        this.userDAO = userDAO;
        this.mainView = mainView;
    }

    public void login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            if (user.isAuthorized()) {
                view.showSuccess("Login successful!");
                view.setVisible(false);
                mainView.setCurrentUser(user);  // Adicionado para passar o usuário atual
                mainView.showMainView();
            } else {
                view.showError("User not authorized. Please wait for admin approval.");
            }
        } else {
            view.showError("Invalid username or password.");
        }
    }

    public void register(String username, String password) {
        if (userDAO.findByUsername(username) != null) {
            view.showError("Username already exists.");
            return;
        }

        User newUser = new User(username, password);
        if (userDAO.count() == 0) {
            newUser.setAdmin(true);
            newUser.setAuthorized(true);
        }

        userDAO.create(newUser);
        view.showSuccess("Registration successful! " + 
                         (newUser.isAdmin() ? "You are the admin." : "Please wait for admin approval."));
    }
}

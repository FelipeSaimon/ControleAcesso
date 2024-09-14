/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package command;

import model.User;
import presenter.UserManagementPresenter;

public class AuthorizeUserCommand implements Command {
    private UserManagementPresenter presenter;
    private User user;

    public AuthorizeUserCommand(UserManagementPresenter presenter, User user) {
        this.presenter = presenter;
        this.user = user;
    }

    @Override
    public void execute() {
        presenter.authorizeUser(user);
    }
}

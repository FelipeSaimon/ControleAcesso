/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package command;

import model.User;
import presenter.UserManagementPresenter;

public class DeleteUserCommand implements Command {
    private UserManagementPresenter presenter;
    private User user;

    public DeleteUserCommand(UserManagementPresenter presenter, User user) {
        this.presenter = presenter;
        this.user = user;
    }

    @Override
    public void execute() {
        presenter.deleteUser(user);
    }
}

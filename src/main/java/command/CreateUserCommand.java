/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package command;

import presenter.UserManagementPresenter;

/**
 *
 * @author Erik
 */
public class CreateUserCommand implements Command {
    private UserManagementPresenter presenter;
    private String username;
    private String password;

    public CreateUserCommand(UserManagementPresenter presenter, String username, String password) {
        this.presenter = presenter;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        presenter.createUser(username, password);
    }
}

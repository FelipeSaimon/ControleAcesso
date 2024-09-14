
package state;


import model.User;
import view.UserManagementView;
import presenter.UserManagementPresenter;

public class UserManagementContext {
    private UserManagementState state;
    private UserManagementPresenter presenter;
    private UserManagementView view;

    public UserManagementContext(UserManagementPresenter presenter, UserManagementView view) {
        this.presenter = presenter;
        this.view = view;
        this.state = new CreateUserState(presenter, view);
    }

    public void setState(UserManagementState state) {
        this.state = state;
    }

    public void createUser(String username, String password) {
        state.createUser(username, password);
    }

    public void updateUser(User user) {
        state.updateUser(user);
    }

    public void deleteUser(User user) {
        state.deleteUser(user);
    }

    public void authorizeUser(User user) {
        state.authorizeUser(user);
    }
}

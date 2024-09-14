
package state;


import model.User;
import view.UserManagementView;
import presenter.UserManagementPresenter;

public class CreateUserState implements UserManagementState {
    private UserManagementPresenter presenter;
    private UserManagementView view;

    public CreateUserState(UserManagementPresenter presenter, UserManagementView view) {
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public void createUser(String username, String password) {
        presenter.createUser(username, password);
    }

    @Override
    public void updateUser(User user) {
        view.showError("Cannot update user in Create state.");
    }

    @Override
    public void deleteUser(User user) {
        view.showError("Cannot delete user in Create state.");
    }

    @Override
    public void authorizeUser(User user) {
        view.showError("Cannot authorize user in Create state.");
    }
}

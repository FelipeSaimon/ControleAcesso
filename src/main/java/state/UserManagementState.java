package state;


import model.User;
import view.UserManagementView;

public interface UserManagementState {
    void createUser(String username, String password);
    void updateUser(User user);
    void deleteUser(User user);
    void authorizeUser(User user);

}
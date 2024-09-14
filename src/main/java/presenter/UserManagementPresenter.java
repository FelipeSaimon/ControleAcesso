package presenter;

import dao.UserDAO;
import java.util.List;
import model.User;
import view.UserManagementView;
import com.pss.senha.validacao.ValidadorSenha;
import command.Command;

public class UserManagementPresenter {
    private UserManagementView view;
    private UserDAO userDAO;
    private ValidadorSenha validadorSenha;

    public UserManagementPresenter(UserManagementView view, UserDAO userDAO) {
        this.view = view;
        this.userDAO = userDAO;
        this.validadorSenha = new ValidadorSenha();
    }

    public void loadUsers() {
        List<User> users = userDAO.list();
        view.showUserList(users);
    }

    public void executeCommand(Command command) {
        command.execute();
    }

    public void createUser(String username, String password) {
        if (userDAO.findByUsername(username) != null) {
            view.showError("Username already exists.");
            return;
        }

        List<String> errosSenha = validadorSenha.validar(password);
        if (!errosSenha.isEmpty()) {
            view.showError(errosSenha.get(0));
            return;
        }

        User newUser = new User(username, password);
        userDAO.create(newUser);
        loadUsers();
        view.showSuccess("User created successfully.");
    }

    public void updateUser(User user) {
        if (user == null) {
            view.showError("Por favor, selecione um usuario para atualizar.");
            return;
        }
        view.showUserForm(user);
        userDAO.update(user);
        loadUsers();
        view.showSuccess("Usuario atualizado com sucesso.");
    }

    public void deleteUser(User user) {
        if (user == null) {
            view.showError("Por favor, selecione um usuario para deletar.");
            return;
        }
        userDAO.delete(user);
        loadUsers();
        view.showSuccess("Usuario deletado com sucesso.");
    }

    public void authorizeUser(User user) {
        if (user == null) {
            view.showError("Por favor, selecione um usuario para autorizar.");
            return;
        }
        user.setAuthorized(true);
        userDAO.update(user);
        loadUsers();
        view.showSuccess("Usuario autorizado com sucesso.");
    }
}
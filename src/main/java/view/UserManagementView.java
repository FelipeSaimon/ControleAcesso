package view;

import command.AuthorizeUserCommand;
import command.CreateUserCommand;
import command.DeleteUserCommand;
import command.UpdateUserCommand;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.User;
import presenter.UserManagementPresenter;

public class UserManagementView extends JFrame {
    private JList<User> userList;
    private DefaultListModel<User> listModel;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton authorizeButton;
    private UserManagementPresenter presenter;
    private JLabel usernameLabel;

    public UserManagementView() {
        User user = new User();
        setTitle("Gestão de usuarios");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.usernameLabel = new JLabel("Usuário logado: " + user.getUsername());


        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        add(new JScrollPane(userList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        createButton = new JButton("Criar");
        updateButton = new JButton("Alterar");
        deleteButton = new JButton("Deletar");
        authorizeButton = new JButton("Autorizar");

        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(authorizeButton);
        buttonPanel.add(this.usernameLabel);

        add(buttonPanel, BorderLayout.SOUTH);

        createButton.addActionListener(e -> showCreateUserDialog());
        updateButton.addActionListener(e -> presenter.executeCommand(new UpdateUserCommand(presenter, userList.getSelectedValue())));
        deleteButton.addActionListener(e -> presenter.executeCommand(new DeleteUserCommand(presenter, userList.getSelectedValue())));
        authorizeButton.addActionListener(e -> presenter.executeCommand(new AuthorizeUserCommand(presenter, userList.getSelectedValue())));
        

    }

    public void setPresenter(UserManagementPresenter presenter) {
        this.presenter = presenter;
    }

    public void showUserList(List<User> users) {
//        listModel.clear();
        for (User user : users) {
            listModel.addElement(user);
        }
    }
    
    private void showCreateUserDialog() {
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Create User",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            presenter.executeCommand(new CreateUserCommand(presenter, username, password));
        }
    }

    public void showUserForm(User user) {
        // TODO: Implement user form dialog
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
package view;


import javax.swing.*;
import java.awt.*;
import presenter.ConfigurationPresenter;

public class ConfigurationView extends JFrame {
    private JComboBox<String> logFormatComboBox;
    private JButton saveButton;
    private ConfigurationPresenter presenter;

    public ConfigurationView() {
        setTitle("Configuração do Sistema");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel.add(new JLabel("Formato do Log:"));
        logFormatComboBox = new JComboBox<>(new String[]{"CSV", "JSON"});
        panel.add(logFormatComboBox);

        saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> presenter.saveConfiguration());

        panel.add(new JLabel());
        panel.add(saveButton);

        add(panel, BorderLayout.CENTER);
    }

    public void setPresenter(ConfigurationPresenter presenter) {
        this.presenter = presenter;
    }

    public String getSelectedLogFormat() {
        return (String) logFormatComboBox.getSelectedItem();
    }

    public void setSelectedLogFormat(String format) {
        logFormatComboBox.setSelectedItem(format);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Successo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presenter;

import log.LogManager;
import model.Log;
import view.ConfigurationView;

/**
 *
 * @author Erik
 */
public class ConfigurationPresenter {
    private ConfigurationView view;
    private LogManager logManager;

    public ConfigurationPresenter(ConfigurationView view, LogManager logManager) {
        this.view = view;
        this.logManager = logManager;
        loadCurrentConfiguration();
    }

    private void loadCurrentConfiguration() {
        String currentFormat = logManager.getLogFormat();
        view.setSelectedLogFormat(currentFormat);
    }

    public void saveConfiguration() {
        String selectedFormat = view.getSelectedLogFormat();
        logManager.setLogFormat(selectedFormat);
        view.showSuccess("Configuration saved successfully.");
        
        // Log the configuration change
        Log configChangeLog = new Log(
            "CONFIGURATION_CHANGE",
            "SYSTEM", // You might want to pass the current user here
            "Log format changed to " + selectedFormat
        );
        logManager.log(configChangeLog);
    }
}

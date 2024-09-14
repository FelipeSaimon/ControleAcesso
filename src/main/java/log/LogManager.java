/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package log;

/**
 *
 * @author Erik
 */
import java.util.prefs.Preferences;
import model.Log;

public class LogManager {
    private ILogAdapter logAdapter;
    private static final String PREF_LOG_FORMAT = "logFormat";
    private Preferences prefs = Preferences.userNodeForPackage(LogManager.class);

    public LogManager() {
        String format = prefs.get(PREF_LOG_FORMAT, "CSV");
        setLogFormat(format);
    }

    public void setLogFormat(String format) {
        if ("JSON".equalsIgnoreCase(format)) {
            logAdapter = new JSONLogAdapter();
        } else {
            logAdapter = new CSVLogAdapter();
        }
        prefs.put(PREF_LOG_FORMAT, format.toUpperCase());
    }

    public String getLogFormat() {
        return prefs.get(PREF_LOG_FORMAT, "CSV");
    }

    public void log(Log logEntry) {
        logAdapter.writeLog(logEntry);
    }
}

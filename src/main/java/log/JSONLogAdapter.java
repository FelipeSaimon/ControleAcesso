/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package log;

/**
 *
 * @author Erik
 */
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import model.Log;

public class JSONLogAdapter implements ILogAdapter {
    private static final String LOG_FILE = "system_log.json";

    @Override
    public void writeLog(Log logEntry) {
        JSONObject jsonLog = new JSONObject();
        jsonLog.put("operation", logEntry.getOperation());
        jsonLog.put("username", logEntry.getUsername());
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        jsonLog.put("date", dateFormat.format(logEntry.getTimestamp()));
        jsonLog.put("time", timeFormat.format(logEntry.getTimestamp()));
        
        jsonLog.put("details", logEntry.getDetails());

        try (FileWriter file = new FileWriter(LOG_FILE, true)) {
            file.write(jsonLog.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


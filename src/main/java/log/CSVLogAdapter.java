/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package log;

/**
 *
 * @author Erik
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import model.Log;

public class CSVLogAdapter implements ILogAdapter {
    private static final String LOG_FILE = "system_log.csv";

    @Override
    public void writeLog(Log logEntry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            
            writer.println(String.join(";",
                logEntry.getOperation(),
                logEntry.getUsername(),
                dateFormat.format(logEntry.getTimestamp()),
                timeFormat.format(logEntry.getTimestamp()),
                logEntry.getDetails()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

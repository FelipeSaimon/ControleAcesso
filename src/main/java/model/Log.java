/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Erik
 */
import java.util.Date;

public class Log {
    private String operation;
    private String username;
    private Date timestamp;
    private String details;

    public Log(String operation, String username, String details) {
        this.operation = operation;
        this.username = username;
        this.timestamp = new Date();
        this.details = details;
    }

    // Getters
    public String getOperation() {
        return operation;
    }

    public String getUsername() {
        return username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, (%s, %s), %s", 
            operation, username, 
            new java.text.SimpleDateFormat("dd/MM/yyyy").format(timestamp),
            new java.text.SimpleDateFormat("HH:mm:ss").format(timestamp),
            details);
    }
}

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

public class User {
    private int id;
    public String username;
    private String password;
    private boolean isAdmin;
    private boolean isAuthorized;
    private Date creationDate;
    private int sentNotificationsCount;
    private int readNotificationsCount;

    public User() {
        this.creationDate = new Date();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
        this.isAdmin = false;
        this.isAuthorized = false;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getSentNotificationsCount() {
        return sentNotificationsCount;
    }

    public void setSentNotificationsCount(int sentNotificationsCount) {
        this.sentNotificationsCount = sentNotificationsCount;
    }

    public int getReadNotificationsCount() {
        return readNotificationsCount;
    }

    public void setReadNotificationsCount(int readNotificationsCount) {
        this.readNotificationsCount = readNotificationsCount;
    }

    public void incrementSentNotificationsCount() {
        this.sentNotificationsCount++;
    }

    public void incrementReadNotificationsCount() {
        this.readNotificationsCount++;
    }

    @Override
    public String toString() {
        return username + (isAdmin ? " (Admin)" : "") + (isAuthorized ? " (Authorized)" : " (Unauthorized)");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observer;

/**
 *
 * @author Erik
 */
import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserManagementSubject {
    private List<UserManagementObserver> observers = new ArrayList<>();
    private User user;

    public void attach(UserManagementObserver observer) {
        observers.add(observer);
    }

    public void detach(UserManagementObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (UserManagementObserver observer : observers) {
            observer.update(user);
        }
    }

    public void setUser(User user) {
        this.user = user;
        notifyObservers();
    }
}

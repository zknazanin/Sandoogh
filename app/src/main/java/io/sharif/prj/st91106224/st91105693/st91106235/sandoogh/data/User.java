package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
    private String image;
    private String username,id;
    private ArrayList<Notification> notifications;

    public User(){
        notifications = new ArrayList<>();
    }

    public User(String uid, String s, String i) {
        this.id = uid;
        this.username = s;
        this.image = i;
        notifications = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void addNotifications(Notification notification) {
        this.notifications.add(notification);
    }
}

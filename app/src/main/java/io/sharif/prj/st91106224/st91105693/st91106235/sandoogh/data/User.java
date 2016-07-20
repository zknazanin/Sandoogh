package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;


import java.io.Serializable;

public class User implements Serializable{
    String image;
    String username,id;
    public User(){
    }

    public User(String uid, String s, String i) {
        this.id = uid;
        this.username = s;
        this.image = i;
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
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;


public class User {
    int image;
    String username,id;
    public User(String n, int image){
        username = n;
        this.image = image;
    }

    public User(String uid, String s, int i) {
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

    public int getImage() {
        return image;
    }
}

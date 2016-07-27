package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;

/**
 * Created by foroughM on 7/26/2016.
 */
public class Notification {
    private String type , id;
    private String sandooghName;
    private String state;


    public Notification() {}

    public String getSandooghName() {
        return sandooghName;
    }

    public String getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSandooghName(String sandooghName) {
        this.sandooghName = sandooghName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

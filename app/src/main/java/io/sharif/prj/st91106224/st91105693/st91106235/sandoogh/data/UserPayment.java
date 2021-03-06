package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;


import java.io.Serializable;

public class UserPayment implements Serializable {

    private boolean approved;
    private String userID;
    private String paymentID;

    public UserPayment(boolean approved, String userID, String paymentID) {
        this.approved = approved;
        this.userID = userID;
        this.paymentID = paymentID;
    }

    public UserPayment() {
    }

    public UserPayment(boolean approved, String userID) {
        this.approved = approved;
        this.userID = userID;
        paymentID = "";
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
}

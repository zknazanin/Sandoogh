package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;


import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;

public class ConfirmPayment {


    private String userID;
    private String paymentID;
    private SolarCalendar deadline;
    private int amount;

    private int paymentListIndex;
    private int userPaymentListIndex;


    public ConfirmPayment(String userID, String paymentID, SolarCalendar deadline, int amount, int paymentListIndex, int userPaymentListIndex) {
        this.userID = userID;
        this.paymentID = paymentID;
        this.deadline = deadline;
        this.amount = amount;
        this.paymentListIndex = paymentListIndex;
        this.userPaymentListIndex = userPaymentListIndex;
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

    public SolarCalendar getDeadline() {
        return deadline;
    }

    public void setDeadline(SolarCalendar deadline) {
        this.deadline = deadline;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPaymentListIndex() {
        return paymentListIndex;
    }

    public void setPaymentListIndex(int paymentListIndex) {
        this.paymentListIndex = paymentListIndex;
    }

    public int getUserPaymentListIndex() {
        return userPaymentListIndex;
    }

    public void setUserPaymentListIndex(int userPaymentListIndex) {
        this.userPaymentListIndex = userPaymentListIndex;
    }
}

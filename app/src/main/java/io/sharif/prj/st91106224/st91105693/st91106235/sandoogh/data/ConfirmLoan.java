package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;


import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;

public class ConfirmLoan {

    private String userID;
    private String paymentID;
    private SolarCalendar deadline;
    private int amount;

    private int loanListIndex;
    private int loanPaymentListIndex;

    public ConfirmLoan(String userID, String paymentID, SolarCalendar deadline, int amount, int loanListIndex, int loanPaymentListIndex) {
        this.userID = userID;
        this.paymentID = paymentID;
        this.deadline = deadline;
        this.amount = amount;
        this.loanListIndex = loanListIndex;
        this.loanPaymentListIndex = loanPaymentListIndex;
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

    public int getLoanListIndex() {
        return loanListIndex;
    }

    public void setLoanListIndex(int loanListIndex) {
        this.loanListIndex = loanListIndex;
    }

    public int getLoanPaymentListIndex() {
        return loanPaymentListIndex;
    }

    public void setLoanPaymentListIndex(int loanPaymentListIndex) {
        this.loanPaymentListIndex = loanPaymentListIndex;
    }
}

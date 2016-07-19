package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;

import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;


public class Payment {
    private ArrayList<UserPayment> userPaymentList;


    private SolarCalendar deadline;
    private int amount;

    public ArrayList<UserPayment> getUserPaymentList() {
        return userPaymentList;
    }

    public void setUserPaymentList(ArrayList<UserPayment> userPaymentList) {
        this.userPaymentList = userPaymentList;
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
}

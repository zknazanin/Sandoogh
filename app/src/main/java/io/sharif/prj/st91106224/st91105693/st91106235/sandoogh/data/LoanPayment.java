package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;


import java.io.Serializable;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;

public class LoanPayment implements Serializable {

    private SolarCalendar deadline;
    private boolean approved;
    private String paymentID;

    public LoanPayment() {

    }

    public LoanPayment(SolarCalendar deadline, boolean approved, String paymentID) {
        this.deadline = deadline;
        this.approved = approved;
        this.paymentID = paymentID;
    }


    public SolarCalendar getDeadline() {
        return deadline;
    }

    public void setDeadline(SolarCalendar deadline) {
        this.deadline = deadline;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;


import java.io.Serializable;
import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;

public class Loan implements Serializable {

    private int amount;
    private SolarCalendar startDate;
    private String userId;
    private String period;
    private int count;
    private ArrayList<LoanPayment> loanPayments;

    public Loan() {

        loanPayments = new ArrayList<>();

    }

    public Loan(LoanRequest loanRequest) {
        this.amount = loanRequest.getAmount();
        startDate = loanRequest.getStartDate();
        this.userId = loanRequest.getUserId();
        this.period = loanRequest.getPeriod();
        this.count = loanRequest.getCount();
        calculateLoanPayments();

    }

    public Loan(int amount, SolarCalendar startDate, String userId, String period, int count, ArrayList<LoanPayment> loanPayments) {
        this.amount = amount;
        this.startDate = startDate;
        this.userId = userId;
        this.period = period;
        this.count = count;
        this.loanPayments = loanPayments;
    }

    private void calculateLoanPayments() {

        loanPayments = new ArrayList<>();

        SolarCalendar currentDate = startDate;

        for (int i = 0; i < count; i++) {

            LoanPayment loanPayment = new LoanPayment();
            loanPayment.setApproved(false);
            loanPayment.setPaymentID("");

            int date = 0, month = 0, year = 0;

            switch (period) {
                case "یک هفته":
                    if (currentDate.getDate() < 8) {
                        date = 8;
                    } else if (currentDate.getDate() < 15) {
                        date = 15;
                    } else if (currentDate.getDate() < 23) {
                        date = 23;
                    } else {
                        date = 1;
                        month = 1;
                    }
                    month += currentDate.getMonth();
                    if (month > 12) {
                        month -= 12;
                        year = 1;
                    }
                    year += currentDate.getYear();
                    break;
                case "یک ماه":
                    date = currentDate.getDate();
                    month = currentDate.getMonth() + 1;
                    if (month > 12) {
                        month -= 12;
                        year = 1;
                    }
                    year += currentDate.getYear();
                    break;
                case "سه ماه":
                    date = currentDate.getDate();
                    month = currentDate.getMonth() + 3;
                    if (month > 12) {
                        month -= 12;
                        year = 1;
                    }
                    year += currentDate.getYear();
                    break;
                case "شش ماه":
                    date = currentDate.getDate();
                    month = currentDate.getMonth() + 6;
                    if (month > 12) {
                        month -= 12;
                        year = 1;
                    }
                    year += currentDate.getYear();
                    break;
            }

            currentDate = new SolarCalendar(date, month, year);
            loanPayment.setDeadline(currentDate);

            loanPayments.add(loanPayment);
        }
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public SolarCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(SolarCalendar startDate) {
        this.startDate = startDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<LoanPayment> getLoanPayments() {
        return loanPayments;
    }

    public void setLoanPayments(ArrayList<LoanPayment> loanPayments) {
        this.loanPayments = loanPayments;
    }


}

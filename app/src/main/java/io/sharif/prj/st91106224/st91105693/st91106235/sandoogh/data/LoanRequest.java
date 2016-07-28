package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;

import java.io.Serializable;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;


public class LoanRequest implements Serializable {
    private int amount;
    private SolarCalendar StartDate;
    private String userId;
    private String period;
    private int count;

    public LoanRequest(int amount, SolarCalendar startDate, String userId, String period, int count) {
        this.amount = amount;
        StartDate = startDate;
        this.userId = userId;
        this.period = period;
        this.count = count;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public SolarCalendar getStartDate() {
        return StartDate;
    }

    public void setStartDate(SolarCalendar startDate) {
        StartDate = startDate;
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
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;

import java.util.Date;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;


public class Sandoogh {
    private int accountNum, cardNum, membersNum;
    private String period, name, type; //type A or B. Type B has not total --> set total = 0
    private int periodPay, total;
    private Date startDate;
    private User[] members;
    private User admin;

    public Sandoogh(int accountNum, int cardNum, String period, String name, String type, int periodPay, User admin) {
        this.accountNum = accountNum;
        this.cardNum = cardNum;
        this.period = period;
        this.name = name;
        this.type = type;
        this.periodPay = periodPay;
        this.admin = admin;
    }

    public Sandoogh(String type) {
        this.type = type;
        if (type.equals("B"))
            total = 0;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public int getMembersNum() {
        return membersNum;
    }

    public void setMembersNum(int membersNum) {
        this.membersNum = membersNum;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPeriodPay() {
        return periodPay;
    }

    public void setPeriodPay(int periodPay) {
        this.periodPay = periodPay;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public User[] getMembers() {
        return members;
    }

    public void setMembers(User[] members) {
        this.members = members;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}

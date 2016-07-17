package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;

import java.io.Serializable;
import java.util.Date;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;


public class Sandoogh implements Serializable {
    private String accountNum;
    private String cardNum;
    private String period, name, type; //type A or B. Type B has not total --> set total = 0
    private int periodPay, total;
    private Date startDate;
    private User[] members;
    private String adminUid;

    public Sandoogh(String type) {
        this.type = type;
        if (type.equals("B"))
            total = 0;
    }

    public Sandoogh() {

    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
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

    public String getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(String adminUid) {
        this.adminUid = adminUid;
    }
}

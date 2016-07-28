package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;

import java.io.Serializable;
import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;


public class Sandoogh implements Serializable {

    private String accountNum;
    private String cardNum;
    private String period, name, type; //type A or B. Type B has not total --> set total = 0
    private int periodPay, total;
    private SolarCalendar startDate;
    private ArrayList<String> memberIds, pendingMembersIds;
    private String adminUid;
    private ArrayList<Payment> paymentList;
    private ArrayList<LoanRequest> loanRequests;


    public Sandoogh() {
        paymentList = new ArrayList<>();
        memberIds = new ArrayList<>();
        pendingMembersIds = new ArrayList<>();
        loanRequests = new ArrayList<>();
    }


    // Copy Constructor
    public Sandoogh(Sandoogh sandoogh) {
        this.accountNum = sandoogh.accountNum;
        this.cardNum = sandoogh.cardNum;
        this.period = sandoogh.period;
        this.name = sandoogh.name;
        this.type = sandoogh.type;
        this.periodPay = sandoogh.periodPay;
        this.total = sandoogh.total;
        this.startDate = sandoogh.startDate;
        this.memberIds = sandoogh.memberIds;
        this.adminUid = sandoogh.adminUid;
        this.paymentList = sandoogh.paymentList;
    }

    public void addNewMemberPayments(String userID) {

        SolarCalendar currentDate = new SolarCalendar();

        for (int i = 0; i < paymentList.size(); i++) {
            if (currentDate.isBefore(paymentList.get(i).getDeadline())) {
                paymentList.get(i).getUserPaymentList().add(new UserPayment(false, userID));
            }
        }
    }

    public void updatePaymentsList() {

        SolarCalendar currentDate = new SolarCalendar();

        while (getLastPayment().getDeadline().isBefore(currentDate)) {
            calculateAndAddNextPayment(getLastPayment().getDeadline());
        }
    }

    public Payment getLastPayment() {
        return paymentList.get(paymentList.size() - 1);
    }

    public void calculateAndAddNextPayment(SolarCalendar startDate) {

        Payment payment = new Payment();
        payment.setAmount(periodPay);

        ArrayList<UserPayment> userPaymentList = new ArrayList<>();
        for (String memberId : memberIds) {
            userPaymentList.add(new UserPayment(false, memberId));
        }
        payment.setUserPaymentList(userPaymentList);

        int date = 0, month = 0, year = 0;

        switch (period) {
            case "یک هفته":
                if (startDate.getDate() < 8) {
                    date = 8;
                } else if (startDate.getDate() < 15) {
                    date = 15;
                } else if (startDate.getDate() < 23) {
                    date = 23;
                } else {
                    date = 1;
                    month = 1;
                }
                month += startDate.getMonth();
                if (month > 12) {
                    month -= 12;
                    year = 1;
                }
                year += startDate.getYear();
                break;
            case "یک ماه":
                date = startDate.getDate();
                month = startDate.getMonth() + 1;
                if (month > 12) {
                    month -= 12;
                    year = 1;
                }
                year += startDate.getYear();
                break;
            case "سه ماه":
                date = startDate.getDate();
                month = startDate.getMonth() + 3;
                if (month > 12) {
                    month -= 12;
                    year = 1;
                }
                year += startDate.getYear();
                break;
            case "شش ماه":
                date = startDate.getDate();
                month = startDate.getMonth() + 6;
                if (month > 12) {
                    month -= 12;
                    year = 1;
                }
                year += startDate.getYear();
                break;
        }

        payment.setDeadline(new SolarCalendar(date, month, year));
        paymentList.add(payment);
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


    public String getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(String adminUid) {
        this.adminUid = adminUid;
    }

    public ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(ArrayList<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public SolarCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(SolarCalendar startDate) {
        this.startDate = startDate;
    }


    public ArrayList<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(ArrayList<String> memberIds) {
        this.memberIds = memberIds;
    }

    public ArrayList<String> getPendingMembersIds() {
        return pendingMembersIds;
    }

    public void setPendingMembersIds(ArrayList<String> pendingMembersIds) {
        this.pendingMembersIds = pendingMembersIds;
    }


    public ArrayList<LoanRequest> getLoanRequests() {
        return loanRequests;
    }

    public void setLoanRequests(ArrayList<LoanRequest> loanRequests) {
        this.loanRequests = loanRequests;
    }
}

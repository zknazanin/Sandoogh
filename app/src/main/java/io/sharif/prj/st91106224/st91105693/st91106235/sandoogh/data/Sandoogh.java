package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;


public class Sandoogh implements Serializable {

    private String accountNum;
    private String cardNum;
    private String period, name, type; //type A or B. Type B has not total --> set total = 0
    private int periodPay, total;
    private SolarCalendar startDate;
    private ArrayList<String> memberIds;
    private String adminUid;
    private ArrayList<Payment> paymentList;


    public Sandoogh() {
        paymentList = new ArrayList<>();
    }

    public void updatePaymentsList(SolarCalendar startDate) {

        SolarCalendar currentDate = new SolarCalendar();

        Payment payment;
        do {
            payment = new Payment();
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

        } while (payment.getDeadline().isBefore(currentDate));

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


}

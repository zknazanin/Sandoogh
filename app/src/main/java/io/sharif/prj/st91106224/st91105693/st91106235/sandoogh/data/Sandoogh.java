package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data;

import java.util.Date;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

/**
 * Created by foroughM on 7/13/2016.
 */
public class Sandoogh {
    int accountNum, cardNum, membersNum;
    String period, name, type; //type A or B. Type B has not total --> set total = 0
    int periodPay, total;
    Date startDate;
    User[] members;
    User admin;
    public void Sandoogh (String type){
        this.type = type;
        if (type.equals("B"))
            total = 0;
    }
}

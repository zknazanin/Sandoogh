package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;

public class AdminPaymentAdapter extends ArrayAdapter<UserPayment> {

    public AdminPaymentAdapter(Context c, ArrayList<UserPayment> items) {
        super(c, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdminPaymentView adminPaymentView = (AdminPaymentView) convertView;
        if (null == adminPaymentView) {
            adminPaymentView = AdminPaymentView.inflate(parent);
        }
        adminPaymentView.setItem(getItem(position));
        return adminPaymentView;
    }


}
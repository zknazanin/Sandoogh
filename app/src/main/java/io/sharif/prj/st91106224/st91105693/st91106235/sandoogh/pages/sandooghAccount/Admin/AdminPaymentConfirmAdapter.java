package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;

public class AdminPaymentConfirmAdapter extends ArrayAdapter<ConfirmPayment> {

    public AdminPaymentConfirmAdapter(Context c, ArrayList<ConfirmPayment> items) {
        super(c, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdminPaymentConfirmView adminPaymentConfirmView = (AdminPaymentConfirmView) convertView;
        if (null == adminPaymentConfirmView) {
            adminPaymentConfirmView = AdminPaymentConfirmView.inflate(parent);
        }
        adminPaymentConfirmView.setItem(getItem(position));
        return adminPaymentConfirmView;
    }


}
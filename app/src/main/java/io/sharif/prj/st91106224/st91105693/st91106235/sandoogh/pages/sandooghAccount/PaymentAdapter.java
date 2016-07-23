package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;

public class PaymentAdapter extends ArrayAdapter<Payment> {

    public PaymentAdapter(Context c, List<Payment> items) {
        super(c, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView paymentView = (ItemView) convertView;
        if (null == paymentView) {
            paymentView = ItemView.inflate(parent);
        }
        paymentView.setItem(getItem(position));
        return paymentView;
    }

}
package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.ConfirmLoan;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;

public class AdminLoanPaymentConfirmAdapter extends ArrayAdapter<ConfirmLoan> {

    public AdminLoanPaymentConfirmAdapter(Context c, ArrayList<ConfirmLoan> items) {
        super(c, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdminLoanPaymentConfirmView adminLoanPaymentConfirmView = (AdminLoanPaymentConfirmView) convertView;
        if (null == adminLoanPaymentConfirmView) {
            adminLoanPaymentConfirmView = AdminLoanPaymentConfirmView.inflate(parent);
        }
        adminLoanPaymentConfirmView.setItem(getItem(position));
        return adminLoanPaymentConfirmView;
    }


}
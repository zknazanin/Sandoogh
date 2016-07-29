package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.LoanRequest;

public class AdminLoanRequestAdapter extends ArrayAdapter<LoanRequest> {

    public AdminLoanRequestAdapter(Context c, ArrayList<LoanRequest> items) {
        super(c, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdminLoanRequestView adminLoanRequestView = (AdminLoanRequestView) convertView;
        if (null == adminLoanRequestView) {
            adminLoanRequestView = AdminLoanRequestView.inflate(parent);
        }
        adminLoanRequestView.setItem(getItem(position));
        return adminLoanRequestView;
    }


}

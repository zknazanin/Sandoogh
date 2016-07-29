package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.LoanRequest;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;

public class AdminLoanRequestView extends RelativeLayout {

    Context context;

    LoanRequest loanRequest;

    private TextView usernameTextView;
    private TextView amountTextView;
    private TextView periodTextView;
    private TextView countTextView;


    public AdminLoanRequestView(Context c) {
        this(c, null);
    }

    public AdminLoanRequestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdminLoanRequestView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.admin_loan_requests_view_children, this, true);
        setupChildren();
    }

    public static AdminLoanRequestView inflate(ViewGroup parent) {
        return (AdminLoanRequestView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_loan_requests_view, parent, false);
    }

    private void setupChildren() {

        usernameTextView = (TextView) findViewById(R.id.username_text_view);
        amountTextView = (TextView) findViewById(R.id.amount_text_view);
        periodTextView = (TextView) findViewById(R.id.loan_period_text_view);
        countTextView = (TextView) findViewById(R.id.loan_count_text_view);

    }

    public void setItem(LoanRequest loanRequest) {

        this.loanRequest = loanRequest;

        FirebaseDatabase.getInstance().getReference().child("Users").child(loanRequest.getUserId()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        usernameTextView.setText(user.getUsername());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        amountTextView.setText(String.valueOf(loanRequest.getAmount()));
        periodTextView.setText(loanRequest.getPeriod());
        countTextView.setText(String.valueOf(loanRequest.getCount()));

    }


    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

}
package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.ConfirmLoan;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

public class AdminLoanPaymentConfirmView extends RelativeLayout {

    Context context;

    ConfirmLoan confirmLoan;

    private TextView usernameTextView;
    private TextView deadlineTextVIew;
    private TextView amountTextView;
    private TextView idTextView;

    public AdminLoanPaymentConfirmView(Context c) {
        this(c, null);
    }

    public AdminLoanPaymentConfirmView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdminLoanPaymentConfirmView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.admin_payment_confirm_view_children, this, true);
        setupChildren();
    }

    public static AdminLoanPaymentConfirmView inflate(ViewGroup parent) {
        return (AdminLoanPaymentConfirmView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_loan_payment_confirm_view, parent, false);
    }

    private void setupChildren() {
        usernameTextView = (TextView) findViewById(R.id.username_text_view);
        deadlineTextVIew = (TextView) findViewById(R.id.payment_deadline_text_view);
        amountTextView = (TextView) findViewById(R.id.payment_amount_text_view);
        idTextView = (TextView) findViewById(R.id.payment_id_text_view);
    }

    public void setItem(ConfirmLoan confirmLoan) {

        this.confirmLoan = confirmLoan;

        deadlineTextVIew.setText(confirmLoan.getDeadline().toString());
        amountTextView.setText(String.valueOf(confirmLoan.getAmount()));
        idTextView.setText(confirmLoan.getPaymentID());
        try {
            FirebaseDatabase.getInstance().getReference().child("Users").child(confirmLoan.getUserID()).addListenerForSingleValueEvent(
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
        } catch (Exception e) {
            Log.e("R", "Error in adminPaymentConfirmView database function " + e);
            Toast.makeText(getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
        }

    }

}

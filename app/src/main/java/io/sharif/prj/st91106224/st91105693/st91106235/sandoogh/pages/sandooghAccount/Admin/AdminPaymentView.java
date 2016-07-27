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
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;

public class AdminPaymentView extends RelativeLayout {

    Context context;

    private TextView usernameTextView;
    private TextView idTextView;
    private TextView approvedTextView;

    public AdminPaymentView(Context c) {
        this(c, null);
    }

    public AdminPaymentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdminPaymentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.admin_payment_view_children, this, true);
        setupChildren();
    }

    public static AdminPaymentView inflate(ViewGroup parent) {
        return (AdminPaymentView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_payment_view, parent, false);
    }

    private void setupChildren() {
        usernameTextView = (TextView) findViewById(R.id.payment_username_text_view);
        idTextView = (TextView) findViewById(R.id.payment_id_text_view);
        approvedTextView = (TextView) findViewById(R.id.payment_approved_text_view);
    }

    public void setItem(UserPayment userPayment) {

        idTextView.setText(userPayment.getPaymentID());
        if (userPayment.isApproved()) {
            approvedTextView.setText(R.string.payment_report_dialog_approved);
        } else {
            approvedTextView.setText(R.string.payment_report_dialog_not_approved);
        }

        FirebaseDatabase.getInstance().getReference().child("Users").child(userPayment.getUserID()).addListenerForSingleValueEvent(
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

    }

}
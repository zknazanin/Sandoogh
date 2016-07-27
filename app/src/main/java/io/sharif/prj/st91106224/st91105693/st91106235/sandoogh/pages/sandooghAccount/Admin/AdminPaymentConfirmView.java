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

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

public class AdminPaymentConfirmView extends RelativeLayout {

    Context context;

    private TextView usernameTextView;
    private TextView deadlineTextVIew;
    private TextView amountTextView;
    private TextView idTextView;

    public AdminPaymentConfirmView(Context c) {
        this(c, null);
    }

    public AdminPaymentConfirmView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdminPaymentConfirmView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.admin_payment_confirm_view_children, this, true);
        setupChildren();
    }

    public static AdminPaymentConfirmView inflate(ViewGroup parent) {
        return (AdminPaymentConfirmView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_payment_confirm_view, parent, false);
    }

    private void setupChildren() {
        usernameTextView = (TextView) findViewById(R.id.username_text_view);
        deadlineTextVIew = (TextView) findViewById(R.id.payment_deadline_text_view);
        amountTextView = (TextView) findViewById(R.id.payment_amount_text_view);
        idTextView = (TextView) findViewById(R.id.payment_id_text_view);
    }

    public void setItem(ConfirmPayment confirmPayment) {

        deadlineTextVIew.setText(confirmPayment.getDeadline().toString());
        amountTextView.setText(String.valueOf(confirmPayment.getAmount()));
        idTextView.setText(confirmPayment.getPaymentID());

        FirebaseDatabase.getInstance().getReference().child("Users").child(confirmPayment.getUserID()).addListenerForSingleValueEvent(
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

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.User;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;

public class PaymentView extends RelativeLayout {

    private TextView deadlineTextView;
    private TextView amountTextView;
    private TextView idTextView;
    private TextView approvedTextView;

    public PaymentView(Context c) {
        this(c, null);
    }

    public PaymentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaymentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.payment_view_children, this, true);
        setupChildren();
    }

    public static PaymentView inflate(ViewGroup parent) {
        return (PaymentView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_view, parent, false);
    }

    private void setupChildren() {
        deadlineTextView = (TextView) findViewById(R.id.payment_deadline_text_view);
        amountTextView = (TextView) findViewById(R.id.payment_amount_text_view);
        idTextView = (TextView) findViewById(R.id.payment_id_text_view);
        approvedTextView = (TextView) findViewById(R.id.payment_approved_text_view);
    }

    public void setItem(Payment payment) {
        deadlineTextView.setText(payment.getDeadline().toString());
        amountTextView.setText(String.valueOf(payment.getAmount()));

        String currentUserID = Database.getInstance().getCurrentUserID();

        ArrayList<UserPayment> userPaymentList = payment.getUserPaymentList();

        for (int i = 0; i < userPaymentList.size(); i++) {
            if (userPaymentList.get(i).getUserID().equals(currentUserID)) {
                idTextView.setText(userPaymentList.get(i).getPaymentID());
                if (userPaymentList.get(i).isApproved()) {
                    approvedTextView.setText(R.string.payment_report_dialog_approved);
                } else {
                    approvedTextView.setText(R.string.payment_report_dialog_not_approved);
                }
                break;
            }
        }

    }

}
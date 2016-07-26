package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;

public class AdminPaymentView extends RelativeLayout {

    Context context;

    private TextView deadlineTextView;
    private TextView amountTextView;

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
        deadlineTextView = (TextView) findViewById(R.id.payment_deadline_text_view);
        amountTextView = (TextView) findViewById(R.id.payment_amount_text_view);
    }

    public void setItem(Payment payment) {
        amountTextView.setText(String.valueOf(payment.getAmount()));
        deadlineTextView.setText(payment.getDeadline().toString());
    }

}
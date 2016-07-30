package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.User;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Loan;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.LoanPayment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.LoanRequest;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Notification;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin.AdminPanelFragment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;


public class SandooghAccountFragment extends Fragment {
    private ViewGroup view;
    private TextView temp;
    private Button memberButton;
    private ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        this.container = container;
        Bundle bundle = getArguments();
        final Sandoogh sandoogh = (Sandoogh) bundle.getSerializable("SELECTED_SANDOOGH");

        view = (ViewGroup) inflater.inflate(R.layout.sandoogh_account, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(sandoogh.getName());
        populateUi(sandoogh);
        setExpandButtonsFunction(R.id.sandoogh_description_expand_layout, R.id.sandoogh_description_expand_button, R.id.sandoogh_description_layout);
        setExpandButtonsFunction(R.id.sandoogh_payments_expand_layout, R.id.sandoogh_payments_expand_button, R.id.sandoogh_payments_layout);
        setExpandButtonsFunction(R.id.sandoogh_loan_expand_layout, R.id.sandoogh_loan_expand_button, R.id.sandoogh_loan_layout);

        view.findViewById(R.id.period_payment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(sandoogh);
            }
        });

        view.findViewById(R.id.payment_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPaymentReportDialog(sandoogh);
            }
        });

        view.findViewById(R.id.sandoogh_loan_request_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showLoanRequestsDialog(sandoogh);
                } catch (Exception e) {
                    Log.e("R", "Error in notification database function " + e);
                    Toast.makeText(getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.sandoogh_pay_loan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPayLoansDialog(sandoogh);
            }
        });

        memberButton = (Button) view.findViewById(R.id.member_Button);
        memberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Members memberFragment = new Members();
                Bundle memberBundle = new Bundle();
                memberBundle.putSerializable("MEMBERS", sandoogh.getMemberIds());
                memberFragment.setArguments(memberBundle);
                getFragmentManager().beginTransaction()
                        .replace(container.getId(), memberFragment).addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void setExpandButtonsFunction(int layoutId, final int imageViewId, final int layoutBelowId) {

        view.findViewById(layoutId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View layoutBelow = view.findViewById(layoutBelowId);

                if (layoutBelow.getVisibility() == View.VISIBLE) {
                    layoutBelow.setVisibility(View.GONE);
                    ((ImageView) view.findViewById(imageViewId)).setImageResource(R.drawable.ic_expand_more_white_24dp);
                } else {
                    layoutBelow.setVisibility(View.VISIBLE);
                    ((ImageView) view.findViewById(imageViewId)).setImageResource(R.drawable.ic_expand_less_white_24dp);
                }
            }
        });

    }


    protected void showPaymentReportDialog(Sandoogh sandoogh) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.payment_report_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        ListView listView = (ListView) promptView.findViewById(R.id.payment_list_view);
        PaymentAdapter paymentAdapter = new PaymentAdapter(getActivity(), sandoogh.getPaymentList());
        listView.setAdapter(paymentAdapter);


        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();

        alert.show();

    }


    protected void showInputDialog(final Sandoogh sandoogh) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.period_payment_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.period_payment_dialog_edit_text);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.period_payment_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            pay(sandoogh, editText.getText().toString());
                        } catch (Exception e) {
                            Log.e("R", "Error in notification database function " + e);
                            Toast.makeText(getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.period_payment_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void pay(Sandoogh sandoogh, String paymentID) {

        Spinner spinner = (Spinner) view.findViewById(R.id.unpaid_deadlines);
        String selected = spinner.getSelectedItem().toString();

        ArrayList<Payment> paymentArrayList = sandoogh.getPaymentList();
        Payment selectedPayment;
        for (int i = 0; i < paymentArrayList.size(); i++) {
            if (paymentArrayList.get(i).getDeadline().toString().equals(selected)) {
                selectedPayment = paymentArrayList.get(i);
                String currentUserID = Database.getInstance().getCurrentUserID();
                for (UserPayment userPayment : selectedPayment.getUserPaymentList()) {
                    if (userPayment.getUserID().equals(currentUserID)) {
                        userPayment.setPaymentID(paymentID);
                        final Notification notification = new Notification();
                        notification.setState("pending");
                        notification.setSandooghName(sandoogh.getName());
                        notification.setType("payment");
                        FirebaseDatabase.getInstance().getReference().child("Users").child(sandoogh.getAdminUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user;
                                user = dataSnapshot.getValue(User.class);
                                user.addNotifications(notification);
                                Database.getInstance().saveUser(user);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
                break;
            }
        }

        Database.getInstance().updateSandooghPayments(sandoogh);
        updatePayments(sandoogh);

    }


    private void populateUi(final Sandoogh sandoogh) {

        updatePayments(sandoogh);

        temp = (TextView) view.findViewById(R.id.san_AccNum_edit);
        temp.setText(sandoogh.getAccountNum());
        temp = (TextView) view.findViewById(R.id.san_CardNum_edit);
        temp.setText(sandoogh.getCardNum());
        temp = (TextView) view.findViewById(R.id.member_num_edit);
        temp.setText(String.valueOf(sandoogh.getMemberIds().size()));
        temp = (TextView) view.findViewById(R.id.san_Period_edit);
        temp.setText(sandoogh.getPeriod());
        temp = (TextView) view.findViewById(R.id.san_amount_edit);
        temp.setText(String.valueOf(sandoogh.getPeriodPay()));
        temp = (TextView) view.findViewById(R.id.start_date_edit);
        temp.setText(sandoogh.getStartDate().toString());
        if (sandoogh.getType().equals("A")) {
            temp = (TextView) view.findViewById(R.id.san_total_edit);
            temp.setText(String.valueOf(sandoogh.getTotal()));
        }


        List<String> spinnerArray = new ArrayList<>();

        for (int i = 0; i < sandoogh.getLoans().size(); i++) {
            Loan loan = sandoogh.getLoans().get(i);
            if (loan.getUserId().equals(Database.getInstance().getCurrentUserID())) {
                for (int j = 0; j < loan.getLoanPayments().size(); j++) {
                    if (loan.getLoanPayments().get(j).getPaymentID() == null) {
                        spinnerArray.add(loan.getLoanPayments().get(j).getDeadline().toString());
                    }
                }
            }
        }

        if (spinnerArray.size() == 0) {
            view.findViewById(R.id.loan_pay_button_and_spinner_layout).setVisibility(View.GONE);
        } else {
            Spinner spinner = (Spinner) view.findViewById(R.id.loan_deadlines);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        if (sandoogh.getAdminUid().equals(Database.getInstance().getCurrentUserID())) {
            AppCompatButton adminPanelButton = (AppCompatButton) view.findViewById(R.id.admin_panel_button);
            adminPanelButton.setVisibility(View.VISIBLE);
            adminPanelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AdminPanelFragment adminPanelFragment = new AdminPanelFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SANDOOGH", sandoogh);

                    adminPanelFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(container.getId(), adminPanelFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    private void updatePayments(Sandoogh sandoogh) {

        TextView paymentTextView = (TextView) view.findViewById(R.id.sandoogh_payments_text_view);
        ArrayList<Payment> paymentArrayList = sandoogh.getPaymentList();
        String currentUserID = Database.getInstance().getCurrentUserID();
        ArrayList<Integer> notPaidIndices = new ArrayList<>();
        List<String> spinnerArray = new ArrayList<>();
        View payUnpaidsLayout = view.findViewById(R.id.pay_button_and_spinner_layout);

        for (int i = 0; i < paymentArrayList.size(); i++) {
            ArrayList<UserPayment> userPayments = paymentArrayList.get(i).getUserPaymentList();

            for (UserPayment userPayment : userPayments) {
                if (userPayment.getUserID().equals(currentUserID)) {
                    if (userPayment.getPaymentID().equals("")) {
                        notPaidIndices.add(i);
                        spinnerArray.add(paymentArrayList.get(i).getDeadline().toString());
                    }
                }
            }
        }

        if (notPaidIndices.size() != 0) {

            payUnpaidsLayout.setVisibility(View.VISIBLE);
            paymentTextView.setVisibility(View.GONE);

            Spinner spinner = (Spinner) view.findViewById(R.id.unpaid_deadlines);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } else {
            payUnpaidsLayout.setVisibility(View.GONE);
            paymentTextView.setVisibility(View.VISIBLE);
            paymentTextView.setText(R.string.all_paid);
        }
    }


    private void showLoanRequestsDialog(final Sandoogh sandoogh) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.loan_request_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();

        promptView.findViewById(R.id.save_loan_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount = ((EditText) promptView.findViewById(R.id.loan_amount_edit_text)).getText().toString();
                String period = ((Spinner) promptView.findViewById(R.id.loan_period)).getSelectedItem().toString();
                String count = ((EditText) promptView.findViewById(R.id.loan_count_edit_text)).getText().toString();

                if (amount.equals("") || count.equals("")) {
                    Toast.makeText(getActivity(), R.string.loan_request_dialog_empty_fields_error, Toast.LENGTH_LONG).show();
                } else {
                    LoanRequest loanRequest = new LoanRequest(Integer.valueOf(amount), new SolarCalendar(),
                            Database.getInstance().getCurrentUserID(), period, Integer.valueOf(count));
                    Database.getInstance().saveLoanRequest(sandoogh, loanRequest);
                    alert.cancel();
                }
            }
        });

        promptView.findViewById(R.id.cancel_loan_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });

        alert.setTitle(R.string.loan_request);
        alert.show();
    }


    private void showPayLoansDialog(final Sandoogh sandoogh) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.loan_payment_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.loan_payment_id_edit_text);
        TextView dateTextView = (TextView) promptView.findViewById(R.id.date_text_view);

        final Spinner spinner = (Spinner) view.findViewById(R.id.loan_deadlines);
        dateTextView.setText(spinner.getSelectedItem().toString());


        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();

        promptView.findViewById(R.id.save_loan_payment_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loanPay(sandoogh, spinner.getSelectedItem().toString(), editText.getText().toString());
                alert.cancel();
            }
        });

        promptView.findViewById(R.id.cancel_loan_payment_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });


        alert.show();
    }

    private void loanPay(Sandoogh sandoogh, String paymentDeadline, String paymentId) {

        for (int i = 0; i < sandoogh.getLoans().size(); i++) {
            Loan loan = sandoogh.getLoans().get(i);
            if (loan.getUserId().equals(Database.getInstance().getCurrentUserID())) {
                for (int j = 0; j < loan.getLoanPayments().size(); j++) {
                    if (loan.getLoanPayments().get(j).getDeadline().toString().equals(paymentDeadline)) {
                        loan.getLoanPayments().get(j).setPaymentID(paymentId);
                    }
                }
            }
        }

        Database.getInstance().saveLoanPayment(sandoogh);

    }

}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.ConfirmLoan;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Loan;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.LoanPayment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.LoanRequest;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;

public class AdminPanelFragment extends Fragment {

    ViewGroup view;
    ArrayList<String> changedMemberIds;

    private AlertDialog confirmPaymentsDialog, paymentsReportDialog, editMembersDialog,
            loansRequestDialog, loansDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        final Sandoogh sandoogh = (Sandoogh) bundle.getSerializable("SANDOOGH");
        view = (ViewGroup) inflater.inflate(R.layout.admin_panel_fragment, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        populateUi(sandoogh);

        return view;

    }

    private void populateUi(final Sandoogh sandoogh) {

        EditText temp = (EditText) view.findViewById(R.id.san_AccNum_edit);
        temp.setText(sandoogh.getAccountNum());

        temp = (EditText) view.findViewById(R.id.san_CardNum_edit);
        temp.setText(sandoogh.getCardNum());

        ArrayList<String> periodList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.period_Array)));
        ((Spinner) view.findViewById(R.id.period_spinner)).setSelection(periodList.indexOf(sandoogh.getPeriod()));

        temp = (EditText) view.findViewById(R.id.san_amount_edit);
        temp.setText(String.valueOf(sandoogh.getPeriodPay()));


        List<String> spinnerArray = new ArrayList<>();
        for (int i = 0; i < sandoogh.getPaymentList().size(); i++) {
            spinnerArray.add(sandoogh.getPaymentList().get(i).getDeadline().toString());
        }
        Spinner spinner = (Spinner) view.findViewById(R.id.payment_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        view.findViewById(R.id.edit_members_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditMembersDialog(sandoogh);
            }
        });

        view.findViewById(R.id.view_payments_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPaymentsDialog(sandoogh);
            }
        });

        view.findViewById(R.id.confirm_payments_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmPaymentsDialog(sandoogh);
            }
        });

        view.findViewById(R.id.view_loans_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoansDialog(sandoogh);
            }
        });


        view.findViewById(R.id.view_loan_requests_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoanRequestsDialog(sandoogh);
            }
        });


        view.findViewById(R.id.cancel_changes_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.save_changes_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges(sandoogh);
            }
        });


    }

    private void showEditMembersDialog(final Sandoogh sandoogh) {

        if (editMembersDialog == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            final View promptView = layoutInflater.inflate(R.layout.edit_members_dialog, null);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setView(promptView);
            final ListView listView = (ListView) promptView.findViewById(R.id.user_list_view);

            final List<User> users = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(
                    new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                if (sandoogh.getMemberIds().contains(child.getKey())) {
                                    users.add(child.getValue(User.class));
                                }
                            }
                            UserAdapter userAdapter = new UserAdapter(getActivity(), users, sandoogh.getAdminUid());
                            listView.setAdapter(userAdapter);

                            // create an alert dialog
                            editMembersDialog = alertDialogBuilder.create();

                            promptView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getChangedMemberIdsList(listView);
                                    editMembersDialog.cancel();
                                }
                            });

                            editMembersDialog.setTitle(R.string.edit_members);

                            editMembersDialog.show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        } else {
            editMembersDialog.show();
        }
    }

    private void getChangedMemberIdsList(ListView listView) {

        changedMemberIds = new ArrayList<>();

        for (int i = 0; i < listView.getChildCount(); i++) {

            UserView userView = (UserView) getViewByPosition(i, listView);
            if (!userView.isToDelete()) {
                changedMemberIds.add(userView.user.getId());
            }
        }
    }

    private void showPaymentsDialog(Sandoogh sandoogh) {

        Spinner spinner = (Spinner) view.findViewById(R.id.payment_spinner);
        int selected = spinner.getSelectedItemPosition();

        final LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View promptView = layoutInflater.inflate(R.layout.admin_payment_report_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        ((TextView) promptView.findViewById(R.id.date_text_view)).setText(
                sandoogh.getPaymentList().get(selected).getDeadline().toString());

        ((TextView) promptView.findViewById(R.id.amount_text_view)).setText(
                String.valueOf(sandoogh.getPaymentList().get(selected).getAmount()));

        ListView listView = (ListView) promptView.findViewById(R.id.payment_list_view);
        AdminPaymentAdapter adminPaymentAdapter = new AdminPaymentAdapter(getActivity(),
                sandoogh.getPaymentList().get(selected).getUserPaymentList());
        listView.setAdapter(adminPaymentAdapter);

        paymentsReportDialog = alertDialogBuilder.create();

        paymentsReportDialog.setTitle(R.string.view_payments);

        promptView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentsReportDialog.cancel();
            }
        });

        paymentsReportDialog.show();

    }

    private void showConfirmPaymentsDialog(Sandoogh sandoogh) {

        if (confirmPaymentsDialog == null) {

            final LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            final View promptView = layoutInflater.inflate(R.layout.admin_confirm_payment_dialog, null);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setView(promptView);

            ListView listView = (ListView) promptView.findViewById(R.id.payment_list_view);

            ArrayList<Payment> allPayments = sandoogh.getPaymentList();
            ArrayList<ConfirmPayment> notConfirmedPayments = new ArrayList<>();

            for (int i = 0; i < allPayments.size(); i++) {

                for (int j = 0; j < allPayments.get(i).getUserPaymentList().size(); j++) {

                    UserPayment userPayment = allPayments.get(i).getUserPaymentList().get(j);

                    if (!userPayment.isApproved()) {
                        notConfirmedPayments.add(new ConfirmPayment(userPayment.getUserID(),
                                userPayment.getPaymentID(), allPayments.get(i).getDeadline(),
                                allPayments.get(i).getAmount(), i, j));
                    }
                }
            }


            AdminPaymentConfirmAdapter adminPaymentConfirmAdapter =
                    new AdminPaymentConfirmAdapter(getActivity(), notConfirmedPayments);
            listView.setAdapter(adminPaymentConfirmAdapter);

            confirmPaymentsDialog = alertDialogBuilder.create();

            confirmPaymentsDialog.setTitle(R.string.confirm_payments);

            promptView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmPaymentsDialog.cancel();
                }
            });
        }

        confirmPaymentsDialog.show();

    }

    private void showLoansDialog(Sandoogh sandoogh) {

        if (loansDialog == null) {

            final LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            final View promptView = layoutInflater.inflate(R.layout.admin_confirm_loan_payment_dialog, null);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setView(promptView);

            ListView listView = (ListView) promptView.findViewById(R.id.loan_payment_list_view);

            ArrayList<Loan> allLoans = sandoogh.getLoans();
            ArrayList<ConfirmLoan> notConfirmedLoans = new ArrayList<>();

            for (int i = 0; i < allLoans.size(); i++) {
                for (int j = 0; j < allLoans.get(i).getLoanPayments().size(); j++) {

                    LoanPayment loanPayment = allLoans.get(i).getLoanPayments().get(j);

                    int amount = allLoans.get(i).getAmount() / allLoans.get(i).getCount();

                    if (!loanPayment.isApproved()) {
                        notConfirmedLoans.add(new ConfirmLoan(allLoans.get(i).getUserId(),
                                loanPayment.getPaymentID(), loanPayment.getDeadline(),
                                amount, i, j));
                    }
                }
            }

            AdminLoanPaymentConfirmAdapter adminLoanPaymentConfirmAdapter = new
                    AdminLoanPaymentConfirmAdapter(getActivity(), notConfirmedLoans);
            listView.setAdapter(adminLoanPaymentConfirmAdapter);

            loansDialog = alertDialogBuilder.create();

            loansDialog.setTitle(R.string.confirm_loan_payments);

            promptView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loansDialog.cancel();
                }
            });
        }

        loansDialog.show();

    }

    private void showLoanRequestsDialog(Sandoogh sandoogh) {

        if (loansRequestDialog == null) {

            final LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            final View promptView = layoutInflater.inflate(R.layout.admin_loan_request, null);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setView(promptView);

            ListView listView = (ListView) promptView.findViewById(R.id.loan_request_list_view);

            AdminLoanRequestAdapter adminLoanRequestAdapter =
                    new AdminLoanRequestAdapter(getActivity(), sandoogh.getLoanRequests());
            listView.setAdapter(adminLoanRequestAdapter);

            loansRequestDialog = alertDialogBuilder.create();

            loansRequestDialog.setTitle(R.string.view_loan_requests);

            promptView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loansRequestDialog.cancel();
                }
            });
        }

        loansRequestDialog.show();
    }

    private void confirmPayments(Sandoogh sandoogh) {
        if (confirmPaymentsDialog != null) {
            ListView listView = (ListView) confirmPaymentsDialog.findViewById(R.id.payment_list_view);

            for (int i = 0; i < listView.getChildCount(); i++) {
                AdminPaymentConfirmView adminPaymentConfirmView = (AdminPaymentConfirmView) getViewByPosition(i, listView);

                if (((CheckBox) adminPaymentConfirmView.findViewById(R.id.checkBox)).isChecked()) {
                    try {
                        Database.getInstance().saveConfirmedPayment(sandoogh,
                                adminPaymentConfirmView.confirmPayment);

                        // Update sandoogh total
                        sandoogh.setTotal(sandoogh.getTotal() + adminPaymentConfirmView.confirmPayment.getAmount());

                    } catch (Exception e) {
                        Log.e("R", "Error in saveConfirmPayment database function " + e);
                        Toast.makeText(getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }

    private void confirmLoanRequests(Sandoogh sandoogh) {

        if (loansRequestDialog != null) {

            ArrayList<LoanRequest> newLoanRequestList = new ArrayList<>();
            ArrayList<Loan> acceptedLoans = new ArrayList<>();

            ListView listView = (ListView) loansRequestDialog.findViewById(R.id.loan_request_list_view);

            for (int i = 0; i < listView.getChildCount(); i++) {
                AdminLoanRequestView adminLoanRequestView = (AdminLoanRequestView) getViewByPosition(i, listView);
                RadioGroup radioGroup = (RadioGroup) adminLoanRequestView.findViewById(R.id.loan_request_radio_group);

                switch (radioGroup.getCheckedRadioButtonId()) {

                    case R.id.loan_request_check:
                        newLoanRequestList.add(adminLoanRequestView.getLoanRequest());
                        break;

                    case R.id.loan_request_accept:
                        acceptedLoans.add(new Loan(adminLoanRequestView.getLoanRequest()));
                        sandoogh.setTotal(sandoogh.getTotal() - adminLoanRequestView.getLoanRequest().getAmount());
                        break;
                }
            }

            sandoogh.setLoanRequests(newLoanRequestList);
            sandoogh.getLoans().addAll(acceptedLoans);
        }
    }

    private void confirmLoans(Sandoogh sandoogh) {

        if (loansDialog != null) {
            ListView listView = (ListView) loansDialog.findViewById(R.id.loan_payment_list_view);

            for (int i = 0; i < listView.getChildCount(); i++) {
                AdminLoanPaymentConfirmView adminLoanPaymentConfirmView = (AdminLoanPaymentConfirmView) getViewByPosition(i, listView);
                ConfirmLoan confirmLoan = adminLoanPaymentConfirmView.confirmLoan;
                if (((CheckBox) adminLoanPaymentConfirmView.findViewById(R.id.checkBox)).isChecked()) {
                    try {
                        sandoogh.getLoans().get(confirmLoan.getLoanListIndex()).getLoanPayments()
                                .get(confirmLoan.getLoanPaymentListIndex()).setApproved(true);

                        // Update sandoogh total
                        sandoogh.setTotal(sandoogh.getTotal() + confirmLoan.getAmount());

                    } catch (Exception e) {
                        Log.e("R", "Error in saveConfirmPayment database function " + e);
                        Toast.makeText(getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            Database.getInstance().saveLoanPayment(sandoogh);

        }

    }


    private void saveChanges(Sandoogh sandoogh) {

        sandoogh.setAccountNum(((EditText) view.findViewById(R.id.san_AccNum_edit)).getText().toString());
        sandoogh.setCardNum(((EditText) view.findViewById(R.id.san_CardNum_edit)).getText().toString());
        sandoogh.setPeriod(((Spinner) view.findViewById(R.id.period_spinner)).getSelectedItem().toString());
        sandoogh.setPeriodPay(Integer.valueOf(((EditText) view.findViewById(R.id.san_amount_edit)).getText().toString()));

        if (editMembersDialog != null) {
            sandoogh.setMemberIds(changedMemberIds);
        }

        confirmLoanRequests(sandoogh);
        confirmPayments(sandoogh);
        confirmLoans(sandoogh);

        try {
            Database.getInstance().saveSandoogh(sandoogh);
        } catch (Exception e) {
            Log.e("R", "Error in saveSandoogh in admin database function " + e);
            Toast.makeText(getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
        }

        getActivity().onBackPressed();
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.sandoogh_admin_panel);
    }
}

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Payment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.UserPayment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;

public class AdminPanelFragment extends Fragment {

    ViewGroup view;
    ArrayList<String> changedMemberIds;


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
                showConfirmPaymentsDialog();
            }
        });

        view.findViewById(R.id.view_loans_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoansDialog();
            }
        });


        view.findViewById(R.id.view_loan_requests_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoanRequestsDialog();
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
                        UserAdapter userAdapter = new UserAdapter(getActivity(), users);
                        listView.setAdapter(userAdapter);

                        // create an alert dialog
                        final AlertDialog alert = alertDialogBuilder.create();

                        promptView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getChangedMemberIdsList(listView);
                                alert.cancel();
                            }
                        });

                        alert.show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    private void showConfirmPaymentsDialog() {

    }

    private void showLoansDialog() {

    }

    private void showLoanRequestsDialog() {

    }


    private void saveChanges(Sandoogh sandoogh) {

        sandoogh.setAccountNum(((EditText) view.findViewById(R.id.san_AccNum_edit)).getText().toString());
        sandoogh.setCardNum(((EditText) view.findViewById(R.id.san_CardNum_edit)).getText().toString());
        sandoogh.setPeriod(((Spinner) view.findViewById(R.id.period_spinner)).getSelectedItem().toString());
        sandoogh.setPeriodPay(Integer.valueOf(((EditText) view.findViewById(R.id.san_amount_edit)).getText().toString()));
        sandoogh.setMemberIds(changedMemberIds);

        Database.getInstance().saveSandoogh(sandoogh);

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
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;


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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.Tools;

public class AdminPanelFragment extends Fragment {

    ViewGroup view;

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


        view.findViewById(R.id.edit_members_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditMembersDialog(sandoogh);
            }
        });

        view.findViewById(R.id.view_payments_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPaymentsDialog();
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
        View promptView = layoutInflater.inflate(R.layout.edit_members_dialog, null);
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
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void showPaymentsDialog() {

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

    }
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;


public class SandooghAccountFragment extends Fragment {
    ViewGroup view;
    EditText temp;
    Button memberButton;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        final Sandoogh sandoogh = (Sandoogh) bundle.getSerializable("SELECTED_SANDOOGH");

        view = (ViewGroup) inflater.inflate(R.layout.sandoogh_account, container, false);

//        if (sandoogh.getType().equals("A")) {
//            view = (ViewGroup) inflater.inflate(R.layout.san_account_a, container, false);
//        } else {
//            view = (ViewGroup) inflater.inflate(R.layout.san_account_b, container, false);
//        }

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        populateUi(sandoogh);


        setExpandButtonsFunction(R.id.sandoogh_description_expand_layout, R.id.sandoogh_description_expand_button, R.id.sandoogh_description_layout);
        setExpandButtonsFunction(R.id.sandoogh_payments_expand_layout, R.id.sandoogh_payments_expand_button, R.id.sandoogh_payments_layout);
        setExpandButtonsFunction(R.id.sandoogh_loan_expand_layout, R.id.sandoogh_loan_expand_button, R.id.sandoogh_loan_layout);

        Button periodPaymentButton = (Button) view.findViewById(R.id.period_payment_button);
        periodPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
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
                        .replace(container.getId(), memberFragment)
                        .commit();
            }
        });

        return view;
    }

    private void setExpandButtonsFunction(int layoutId, final int imageButtonId, final int layoutBelowId) {

        view.findViewById(layoutId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View layoutBelow = view.findViewById(layoutBelowId);

                if (layoutBelow.getVisibility() == View.VISIBLE) {
                    layoutBelow.setVisibility(View.GONE);
                    ((ImageButton) view.findViewById(imageButtonId)).setImageResource(R.drawable.ic_expand_more_white_24dp);
                } else {
                    layoutBelow.setVisibility(View.VISIBLE);
                    ((ImageButton) view.findViewById(imageButtonId)).setImageResource(R.drawable.ic_expand_less_white_24dp);
                }
            }
        });

    }


    protected void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.period_payment_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.period_payment_dialog_edit_text);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.period_payment_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

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


    private void populateUi(Sandoogh sandoogh) {
        temp = (EditText) view.findViewById(R.id.san_AccNum_edit);
        temp.setText(sandoogh.getAccountNum());
        temp = (EditText) view.findViewById(R.id.san_CardNum_edit);
        temp.setText(sandoogh.getCardNum());
        temp = (EditText) view.findViewById(R.id.member_num_edit);
        temp.setText(String.valueOf(sandoogh.getMemberIds().size()));
        temp = (EditText) view.findViewById(R.id.san_Period_edit);
        temp.setText(sandoogh.getPeriod());
        temp = (EditText) view.findViewById(R.id.san_amount_edit);
        temp.setText(String.valueOf(sandoogh.getPeriodPay()));
        temp = (EditText) view.findViewById(R.id.start_date_edit);
        temp.setText(sandoogh.getStartDate().toString());
        if (sandoogh.getType().equals("A")) {
            temp = (EditText) view.findViewById(R.id.san_total_edit);
            temp.setText(String.valueOf(sandoogh.getTotal()));
        }
    }

}

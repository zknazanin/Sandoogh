package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.reflect.Field;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;


public class SandooghAccountFragment extends Fragment {
    ImageView menu;
    ViewGroup view;
    EditText temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        Sandoogh sandoogh = (Sandoogh) bundle.getSerializable("SELECTED_SANDOOGH");

        view = (ViewGroup) inflater.inflate(R.layout.sandoogh_account, container, false);

//        if (sandoogh.getType().equals("A")) {
//            view = (ViewGroup) inflater.inflate(R.layout.san_account_a, container, false);
//        } else {
//            view = (ViewGroup) inflater.inflate(R.layout.san_account_b, container, false);
//        }

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

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

        setExpandButtonsFunction(R.id.sandoogh_description_expand_button, R.id.sandoogh_description_layout);
        setExpandButtonsFunction(R.id.sandoogh_payments_expand_button, R.id.sandoogh_payments_layout);

        return view;
    }

    private void setExpandButtonsFunction(int imageButtonId, final int layoutBelowId) {

        view.findViewById(imageButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View layoutBelow = view.findViewById(layoutBelowId);

                if (layoutBelow.getVisibility() == View.VISIBLE) {
                    layoutBelow.setVisibility(View.GONE);
                    ((ImageButton) v).setImageResource(R.drawable.ic_expand_more_white_24dp);
                } else {
                    layoutBelow.setVisibility(View.VISIBLE);
                    ((ImageButton) v).setImageResource(R.drawable.ic_expand_less_white_24dp);
                }
            }
        });

    }

}

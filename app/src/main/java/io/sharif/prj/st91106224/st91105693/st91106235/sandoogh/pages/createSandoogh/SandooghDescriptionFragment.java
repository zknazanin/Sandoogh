package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;

public class SandooghDescriptionFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.san_description, container, false);


        Button sandooghDescriptionConfirmButton = (Button) view.findViewById(R.id.sandoogh_description_confirm_button);
        final ViewPager viewPager = (ViewPager) container.findViewById(R.id.viewpager);

        sandooghDescriptionConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });

        return view;
    }


    public String getAccountNum() {
        return ((EditText) view.findViewById(R.id.account_num)).getText().toString();
    }

    public String getCardNum() {
        return ((EditText) view.findViewById(R.id.card_num)).getText().toString();
    }

    public String getPeriod() {
        Spinner spinner = (Spinner) view.findViewById(R.id.period_spinner);
        return spinner.getSelectedItem().toString();
    }

    public String getName() {
        return ((EditText) view.findViewById(R.id.name)).getText().toString();
    }
}
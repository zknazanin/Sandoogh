package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;


public class SandooghTypeFragment extends Fragment {
    private  String sandooghType;

    public String getSandooghType() {
        return sandooghType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.san_type, container, false);


        Button sandooghType1Button = (Button) view.findViewById(R.id.sandoogh_type_1_button);
        Button sandooghType2Button = (Button) view.findViewById(R.id.sandoogh_type_2_button);

        final ViewPager viewPager = (ViewPager) container.findViewById(R.id.viewpager);
        if(sandooghType == null)
                sandooghType = "none";
        sandooghType1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sandooghType = "A";
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });

        sandooghType2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sandooghType = "B";
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });


        return view;
    }
}
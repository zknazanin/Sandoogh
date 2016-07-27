package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;

public class SandooghConfirmFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.san_confirm, container, false);


        Button sandooghType1Button = (Button) view.findViewById(R.id.sandoogh_confirm_button);

        final ViewPager viewPager = (ViewPager) container.findViewById(R.id.viewpager);

        sandooghType1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    try {
                    ((ViewPagerAdapter) viewPager.getAdapter()).createSandoogh(getActivity(), getFragmentManager());
//                } catch (RuntimeException e){
//                        Log.e("R", "Error in confirm page database function " + e.toString());
//                        Toast.makeText(view.getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
//                    }
            }

        });
        return view;
    }
}
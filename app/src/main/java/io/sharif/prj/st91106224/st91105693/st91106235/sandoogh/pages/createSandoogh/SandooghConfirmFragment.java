package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.home.HomeFragment;

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
                try {
                    ((ViewPagerAdapter) viewPager.getAdapter()).createSandoogh();
                    Toast.makeText(getActivity(), "صندوق با موفقیت ایجاد شد",
                            Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction()
                            .replace(container.getId(), new HomeFragment())
                            .commit();
                } catch (RuntimeException e){
                        Log.e("R", "Error in confirm page database function " + e.toString());
                        Toast.makeText(view.getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
                    }
            }

        });
        return view;
    }
}
package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);
        LinearLayout rl = (LinearLayout)view.findViewById(R.id.ground);
        Button[] b = new Button[3];
        for (int i=0 ; i<3 ; i++){
            b[i] = new Button(getContext());
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
            b[i].setText(R.string.app_name);
            b[i].setGravity(Gravity.CENTER);
            b[i].setLayoutParams(btnParams);
            rl.addView(b[i]);
        }
        return view;
    }
}
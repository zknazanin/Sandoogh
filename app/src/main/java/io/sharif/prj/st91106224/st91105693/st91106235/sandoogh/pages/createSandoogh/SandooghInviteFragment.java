package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;

public class SandooghInviteFragment extends Fragment {

    AutoCompleteTextView textIn;
    Button buttonAdd;
    LinearLayout container1;
    TextView info;
    private static final String[] NUMBER = new String[]{
            "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten"
    };
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.san_invite, container, false);

        Button sandooghInviteConfirmButton = (Button) view.findViewById(R.id.sandoogh_invite_confirm_button);

        final ViewPager viewPager = (ViewPager) container.findViewById(R.id.viewpager);

        sandooghInviteConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });

        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, NUMBER);

        textIn = (AutoCompleteTextView) view.findViewById(R.id.textin);
        textIn.setAdapter(adapter);

        buttonAdd = (Button) view.findViewById(R.id.add);
        container1 = (LinearLayout) view.findViewById(R.id.container);
        info = (TextView) view.findViewById(R.id.info);
        //  info.setMovementMethod(new ScrollingMovementMethod());

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.invite_row, null);
                TextView textOut = (TextView) addView.findViewById(R.id.inviteRow);
                if (Arrays.asList(NUMBER).contains(textIn.getText().toString())) {
                    textOut.setText(textIn.getText().toString());
                    container1.addView(addView);
                    textIn.setText("");
                }
            }
        });


        return view;
    }

    //    private void listAllAddView(){
//        int childCount = container.getChildCount();
//        for(int i=0; i<childCount; i++){
//            View thisChild = container.getChildAt(i);
//            reList.append(thisChild + "\n");
//
//            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.textout);
//            String childTextViewValue = childTextView.getText().toString();
//            reList.append("= " + childTextViewValue + "\n");
//        }
}
package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);
//        LinearLayout rl = (LinearLayout)view.findViewById(R.id.ground);
//        Button[] b = new Button[3];
//        for (int i=0 ; i<3 ; i++){
//            b[i] = new Button(getContext());
//            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
//                    AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
//            b[i].setText(R.string.app_name);
//            b[i].setGravity(Gravity.CENTER);
//            b[i].setLayoutParams(btnParams);
//            rl.addView(b[i]);
//        }
        String[] Values = {"sandoogh 1","sandoogh 2", "sandoogh 3"};
        SandooghArrayAdapter adapter = new SandooghArrayAdapter(getActivity(),Values);
        ListView lv = (ListView)view.findViewById(R.id.list);
        lv.setAdapter(adapter);
        return view;
    }
}

class SandooghArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public SandooghArrayAdapter(Context context, String[] values) {
        super(context, R.layout.button, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.button, parent, false);
        Button btn = (Button) rowView.findViewById(R.id.buttonDy);
        btn.setText(values[position]);
        return rowView;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Get current user from Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        TextView textView = (TextView) view.findViewById(R.id.textview);

        if (user != null) {
            textView.setText(user.getEmail());
        }
        return view;
    }
}
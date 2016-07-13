package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;


public class HomeFragment extends Fragment {

    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);
        String[] Values = {"sandoogh 1","sandoogh 2", "sandoogh 3"};
        SandooghArrayAdapter adapter = new SandooghArrayAdapter(getActivity(),Values);
        ListView lv = (ListView)view.findViewById(R.id.list);
        lv.setAdapter(adapter);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Get current user from Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

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
    }
}
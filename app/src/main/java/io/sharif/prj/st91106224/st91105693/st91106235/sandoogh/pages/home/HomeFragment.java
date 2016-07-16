package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.home;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh.CreateSandooghFragment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.Tools;


public class HomeFragment extends Fragment {

    private ViewGroup view;
    private FirebaseUser user;
    private Sandoogh[] sandooghObjects;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);

        getUserSandooghs();

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Get current user from Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateSandooghFragment createSandooghFragment = new CreateSandooghFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(container.getId(), createSandooghFragment)
                        .commit();
            }
        });


        return view;
    }


    private void getUserSandooghs() {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("sandooghs").addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.wtf("Home Fragment", "On Data Change");

//                        Sandoogh sandoogh = dataSnapshot.getValue(Sandoogh.class);
//                        Log.wtf("test", sandoogh.getName() + " " + sandoogh.getAccountNum() + " " + sandoogh.getAdminUid());
                        List<DataSnapshot> list = Tools.iteratorToList(dataSnapshot.getChildren().iterator());
                        DataSnapshot[] sandooghsDataSnapshots = (DataSnapshot[]) list.toArray();
                        sandooghObjects = new Sandoogh[list.size()];
                        for (int i = 0; i < sandooghsDataSnapshots.length; i++) {
                            sandooghObjects[i] = sandooghsDataSnapshots[i].getValue(Sandoogh.class);
                        }

                        final String[] values = new String[sandooghObjects.length];

                        for (int i = 0; i < sandooghObjects.length; i++) {
                            values[i] = sandooghObjects[i].getName();
                        }


                        SandooghArrayAdapter adapter = new SandooghArrayAdapter(getActivity(), values);
                        ListView lv = (ListView) view.findViewById(R.id.list);
                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.wtf("Home Fragment", "Get user sandooghs failed.");
                    }
                });
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
        btn.setOnClickListener(new android.widget.AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return rowView;
    }
}
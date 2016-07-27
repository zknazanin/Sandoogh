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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh.CreateSandooghFragment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.User.SandooghAccountFragment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.Tools;


public class HomeFragment extends Fragment {

    private ViewGroup view;
    private FirebaseUser user;
    private ArrayList<Sandoogh> sandooghObjects;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // Get current user from Firebase
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
            getUserSandooghs(container, getActivity().getSupportFragmentManager());
        } catch (RuntimeException e) {
            Log.e("R", "Error in home fragment database function " + e.toString());
            Toast.makeText(view.getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
        }
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateSandooghFragment createSandooghFragment = new CreateSandooghFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(container.getId(), createSandooghFragment).addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }


    private void getUserSandooghs(final ViewGroup container, final FragmentManager fragmentManager) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("sandooghs").addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        List<DataSnapshot> list = Tools.iteratorToList(dataSnapshot.getChildren().iterator());
                        DataSnapshot[] sandooghsDataSnapshots = list.toArray(new DataSnapshot[list.size()]);

                        sandooghObjects = new ArrayList<>();
                        for (DataSnapshot sandooghsDataSnapshot : sandooghsDataSnapshots) {
                            Sandoogh temp = sandooghsDataSnapshot.getValue(Sandoogh.class);
                            if (temp.getMemberIds() != null) {
                                if (temp.getMemberIds().contains(user.getUid())) {
                                    sandooghObjects.add(temp);
                                }
                            }
                        }

                        final String[] values = new String[sandooghObjects.size()];

                        for (int i = 0; i < sandooghObjects.size(); i++) {
                            Sandoogh sandoogh = sandooghObjects.get(i);
                            values[i] = sandoogh.getName();
                            sandoogh.updatePaymentsList();
                        }

                        if (getActivity() != null) {
                            SandooghArrayAdapter adapter = new SandooghArrayAdapter(getActivity(),
                                    values, container, fragmentManager, sandooghObjects);
                            ListView lv = (ListView) view.findViewById(R.id.list);
                            lv.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.wtf("Home Fragment", "Get user sandooghs failed.");
                    }
                });
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.toolbar_menu, menu);
//        super.onCreateOptionsMenu(menu,inflater);
//    }

}

class SandooghArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final ViewGroup container;
    private final FragmentManager fragmentManager;
    private final ArrayList<Sandoogh> sandooghObjects;

    public SandooghArrayAdapter(Context context, String[] values, ViewGroup container, FragmentManager fragmentManager1, ArrayList<Sandoogh> sandooghObjects) {
        super(context, R.layout.button, values);
        this.context = context;
        this.values = values;
        this.container = container;
        this.fragmentManager = fragmentManager1;
        this.sandooghObjects = sandooghObjects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.button, parent, false);

        TextView sandooghNameTextView = (TextView) rowView.findViewById(R.id.sandoogh_name_text_view);
        sandooghNameTextView.setText(values[position]);

        ImageView sandooghAdminSign = (ImageView) rowView.findViewById(R.id.sandoogh_admin_sign);
        if (sandooghObjects.get(position).getAdminUid().equals(Database.getInstance().getCurrentUserID())) {
            sandooghAdminSign.setVisibility(View.VISIBLE);
        }

        rowView.setOnClickListener(new android.widget.AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {
                SandooghAccountFragment sandooghAccountFragment = new SandooghAccountFragment();

                Bundle bundle = new Bundle();

                Sandoogh selectedSandoogh = sandooghObjects.get(position);
                bundle.putSerializable("SELECTED_SANDOOGH", selectedSandoogh);

                sandooghAccountFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(container.getId(), sandooghAccountFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return rowView;
    }
}
package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

/**
 * Created by foroughM on 7/17/2016.
 */
public class Members extends Fragment {
    private ViewGroup view;
    private DatabaseReference mDatabase;
    private ArrayList<String> memberIds;
    private int membersCount;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        memberIds = (ArrayList<String>) bundle.getSerializable("MEMBERS");

        view = (ViewGroup) inflater.inflate(R.layout.members, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("اعضا");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        try {
            getListItemData();
        } catch (RuntimeException e) {
            Log.e("R", "error in member page. (get Users)" + e);
            Toast.makeText(view.getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void getListItemData() {
        final List<User> users = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        membersCount = memberIds.size();
        mDatabase.child("Users").addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            if (memberIds.contains(child.getKey())) {
                                users.add(child.getValue(User.class));
                                membersCount--;
                            }
                            if (membersCount == 0)
                                break;
                        }
                        Log.e("R", "" + users.size());
                        MemberAdaptor rcAdapter = new MemberAdaptor(getContext(), users);
                        recyclerView.setAdapter(rcAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.wtf("member Fragment", "Get sandoogh's users failed.");
                    }
                });
    }
}
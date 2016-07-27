package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.Tools;

public class SandooghInviteFragment extends Fragment {

    private AutoCompleteTextView textIn;
    private Button buttonAdd;
    private LinearLayout container1;
    private DatabaseReference mDatabase;
    private List<String> userNames = new ArrayList<>();
    private List<String> users = new ArrayList<>();
    private ArrayList<String> memberIds = new ArrayList<>();
    private ArrayAdapter<String> adapter;

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
        textIn = (AutoCompleteTextView) view.findViewById(R.id.textin);
        buttonAdd = (Button) view.findViewById(R.id.add);
        container1 = (LinearLayout) view.findViewById(R.id.container);
        try {
            getListItemData();
        } catch (RuntimeException e){
            Log.e("R","Error in invite page database function " + e.toString());
            Toast.makeText(view.getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
        }
       // info = (TextView) view.findViewById(R.id.info);
        //  info.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

    public ArrayList<String> getMemberIds() {
        return memberIds;
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
    private void getListItemData(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        List<DataSnapshot> list = Tools.iteratorToList(snapshot.getChildren().iterator());
                        DataSnapshot[] usersDataSnapshots = list.toArray(new DataSnapshot[list.size()]);
                        for (DataSnapshot usersDataSnapshot : usersDataSnapshots)
                            if(!usersDataSnapshot.getValue(User.class).getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                userNames.add(usersDataSnapshot.getValue(User.class).getUsername());
                                users.add(usersDataSnapshot.getValue(User.class).getId());
                            }
                        buttonAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String text = textIn.getText().toString();
                                LayoutInflater layoutInflater =
                                        (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View addView = layoutInflater.inflate(R.layout.invite_row, null);
                                TextView textOut = (TextView) addView.findViewById(R.id.inviteRow);
                                if (userNames.contains(text)) {
                                    textOut.setText(text);
                                    container1.addView(addView);
                                    textIn.setText("");
                                    memberIds.add(users.get(userNames.indexOf(text)));
                                }
                            }
                        });
                        adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, userNames);
                        textIn.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.wtf("invite Fragment", "Get users failed.");
                    }
                });
    }
}
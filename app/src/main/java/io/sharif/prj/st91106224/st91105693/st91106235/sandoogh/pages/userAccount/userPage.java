package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.userAccount;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;


public class userPage extends Fragment {
    private ViewGroup view;
    private ImageView imageView;
    private EditText email, pass, username;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private Button Edit;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.user_account, container, false);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        try {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            firebaseUser = mAuth.getCurrentUser();
            Edit = (Button) view.findViewById(R.id.Edit);
            email = (EditText) view.findViewById(R.id.email_edit);
            pass = (EditText) view.findViewById(R.id.password_edit);
            username = (EditText) view.findViewById(R.id.username_edit);
            imageView = (ImageView) view.findViewById(R.id.image);
            email.setText(firebaseUser.getEmail());
            pass.setText("********");
            mDatabase.child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    final User user = snapshot.getValue(User.class);
                    username.setText(user.getUsername());
                    if (!user.getImage().equals("0")) {
                        String base64Image = user.getImage();
                        byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                        imageView.setImageBitmap(
                                BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                        );
                    }
                    Edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userEdit userEdit = new userEdit();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("USER", user);
                            userEdit.setArguments(bundle);
                            getFragmentManager().beginTransaction()
                                    .replace(container.getId(), userEdit)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("R", "cancel name");
                }
            });
        } catch (Exception e) {
            Log.e("R", "Error in userPage database function " + e);
            Toast.makeText(view.getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
        }
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(R.string.account);
    }
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.userAccount;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
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


public class userPage extends Fragment {
    ViewGroup view;
    ImageView imageView;
    EditText email, pass, username;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;
    Button Edit;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.user_account, container, false);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        try {
            mDatabase = FirebaseDatabase.getInstance().getReference();
//        storage = FirebaseStorage.getInstance();
//        ref = storage.getReference("Images/" + mAuth.getCurrentUser().getUid());
//        Bundle bundle = getArguments();
            firebaseUser = mAuth.getCurrentUser();
            Edit = (Button) view.findViewById(R.id.Edit);
            email = (EditText) view.findViewById(R.id.email_edit);
            pass = (EditText) view.findViewById(R.id.password_edit);
            username = (EditText) view.findViewById(R.id.username_edit);
            imageView = (ImageView) view.findViewById(R.id.image);
            email.setText(firebaseUser.getEmail());
            pass.setText("********");
            mDatabase.child("Users").child(firebaseUser.getUid()).child("username").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String name = (String) snapshot.getValue();
                    username.setText(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("R", "cancel name");
                }
            });




//        if (bundle != null) {
//            downloadUri = (Uri) bundle.getSerializable("URI_DOWN");
//            imageView.setImageURI(downloadUri);
//        }else {
//            final long ONE_MEGABYTE = 1024 * 1024;
//            ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//                 //   imageView.setImageURI(downloadUri);
//                   imageView.setImageBitmap( BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                }
//            });
//        }

            mDatabase.child("Users").child(firebaseUser.getUid()).child("image").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.getValue().toString().equals("0")) {
                        String base64Image = (String) snapshot.getValue();
                        byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                        imageView.setImageBitmap(
                                BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                        );
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("R", "cancel");
                }
            });
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction()
                            .replace(container.getId(), new userEdit())
                            .addToBackStack(null)
                            .commit();
                }
            });
        } catch (RuntimeException e) {
            Log.e("R", "Error in userPage database function " + e);
            Toast.makeText(view.getContext(), R.string.Error, Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}

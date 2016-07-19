package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.userAccount;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;

/**
 * Created by foroughM on 7/17/2016.
 */
public class userEdit extends Fragment {
    ViewGroup view;
    Button selectImage;
    int SELECT_FILE = 100;
    ImageView imageView;
    private FirebaseAuth mAuth;
    EditText username,pass;
    private DatabaseReference mDatabase;
    FirebaseUser firebaseUser;
    String base64Image;
    Button confirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.user_editaccount, container, false);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = mAuth.getCurrentUser();
        pass = (EditText) view.findViewById(R.id.password_edit);
        username = (EditText)view.findViewById(R.id.username_edit);
        confirm = (Button) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(base64Image!=null && base64Image!="0")
                    mDatabase.child("Users").child(firebaseUser.getUid()).child("image").setValue(base64Image);
                if (!pass.getText().toString().equals(""))
                    firebaseUser.updatePassword(pass.getText().toString());
                if(!username.getText().toString().equals(""))
                    mDatabase.child("Users").child(firebaseUser.getUid()).child("username").setValue(username.getText().toString());
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new userPage())
                        .commit();
            }
        });
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
        imageView = (ImageView) view.findViewById(R.id.image);
        selectImage = (Button)view.findViewById(R.id.image_edit);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
            Uri uri = data.getData();
            if(uri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] bytes = baos.toByteArray();
                    base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

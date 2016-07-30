package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.userAccount;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;

public class userEdit extends Fragment {
    private ViewGroup view;
    private Button selectImage, removeImage;
    private int SELECT_FILE = 100;
    private ImageView imageView;
    private EditText username,pass;
    private DatabaseReference mDatabase;
    private String base64Image;
    private Button confirm;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.user_editaccount, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("USER");
        try {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            pass = (EditText) view.findViewById(R.id.password_edit);
            username = (EditText) view.findViewById(R.id.username_edit);
            username.setText(user.getUsername());
            confirm = (Button) view.findViewById(R.id.confirm);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("Users").child(user.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user.setUsername(username.getText().toString());
                            if (base64Image != null) {
                                user.setImage(base64Image);
                            }
                            mDatabase.child("Users").child(user.getId()).setValue(user);
                            getActivity().onBackPressed();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if (!pass.getText().toString().equals(""))
                        Database.getInstance().getCurrentFirebaseUser().updatePassword(pass.getText().toString());
                }
            });
            imageView = (ImageView) view.findViewById(R.id.image);
            selectImage = (Button) view.findViewById(R.id.image_edit);
            removeImage = (Button) view.findViewById(R.id.image_remove);
            if (!user.getImage().equals("0")) {
                String base64Image1 = user.getImage();
                byte[] imageAsBytes = Base64.decode(base64Image1.getBytes(), Base64.DEFAULT);
                imageView.setImageBitmap(
                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                );
            } else {
                removeImage.setVisibility(View.INVISIBLE);
            }
            selectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                }
            });
            removeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    base64Image = "0";
                    imageView.setImageBitmap(null);
                    removeImage.setVisibility(View.INVISIBLE);
                }
            });
        }catch (Exception e){
            Log.e("R","Error in userEdit database function " + e);
            Toast.makeText(view.getContext(),R.string.Error,Toast.LENGTH_SHORT).show();
        }
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
                    removeImage.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(R.string.san_account_edit);
    }
}

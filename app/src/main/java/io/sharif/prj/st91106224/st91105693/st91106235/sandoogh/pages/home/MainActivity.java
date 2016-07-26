package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.help.Help;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.signUpAndLogin.SignUpActivity;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.userAccount.userPage;


public class MainActivity extends AppCompatActivity {

    private Boolean firstTime = null;
    private String[] drawerItemsTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence mTitle;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private  Button notifCount;
    private int mNotifCount = 0;

    static Activity thisActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        thisActivity = this;

//     Button   isUserLoggedIn();

        if (isFirstTime()) {

            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);

        } else {
            setDrawer();
            //setNotification();
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, homeFragment)
                    .commit();


        }
    }

    private void setNotification() {


//        mDatabase.child("Users").child(user.getUid()).child("notifications").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot child : snapshot.getChildren()) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        })
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }
    }


    private void selectItem(int position) {
        // Create a new fragment
        Fragment fragment;
        String mTitle;
        switch (position) {
            case 1:
                fragment = new HomeFragment();
                mTitle = getString(R.string.sandoogh);
                break;
            case 2:
                fragment = new userPage();
                mTitle = getString(R.string.account);
                break;
            case 3:
                fragment = new Help();
                mTitle = getString(R.string.help);
                break;
//            case 4:
//                fragment = new Help();
//                break;
            default:
                fragment = new HomeFragment();
                mTitle = getString(R.string.sandoogh);
        }
        getSupportActionBar().setTitle(mTitle);
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).addToBackStack(null)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        setTitle(drawerItemsTitles[position]);
        drawerLayout.closeDrawer(drawerList);
    }

//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getActionBar().setTitle(mTitle);
//    }


    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();
            }
        }
        return firstTime;
    }

    private void isUserLoggedIn() {
        new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed out
                    Intent intent = new Intent(thisActivity, SignUpActivity.class);
                    startActivity(intent);
                } else {
                    setDrawer();

                    HomeFragment homeFragment = new HomeFragment();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, homeFragment)
                            .commit();
                }
            }
        };
    }

    private void setDrawer() {
        drawerItemsTitles = getResources().getStringArray(R.array.drawer_items_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        View header = getLayoutInflater().inflate(R.layout.header, null);
        final ImageView imageView = (ImageView) header.findViewById(R.id.img_first);

        final TextView textView = (TextView) header.findViewById(R.id.textView);
        try {
            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").child(user.getUid()).child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.getValue().toString().equals("0")) {
                        String base64Image = (String) snapshot.getValue();
                        byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                        imageView.setImageBitmap(
                                BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                        );

                        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        imageView.setImageBitmap(getCircleBitmap(bitmap));

                    }

                    else{
                        imageView.setImageDrawable(getAnonymousDrawable());
                    }

                    Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    imageView.setImageBitmap(getCircleBitmap(bitmap));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mDatabase.child("Users").child(user.getUid()).child("username").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String name = (String) snapshot.getValue();
                    textView.setText(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("R", "cancel name");
                }
            });
        }catch (RuntimeException e){
            Log.e("R","Error in drawer database function " + e);
            Toast.makeText(this, R.string.Error , Toast.LENGTH_SHORT).show();
        }

        drawerList.addHeaderView(header);
        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.activity_list_item, android.R.id.text1, drawerItemsTitles));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    public static Drawable getAnonymousDrawable() {
        return thisActivity.getResources().getDrawable(R.drawable.anonymos);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        View count = menu.findItem(R.id.notif).getActionView();
        notifCount = (Button) count.findViewById(R.id.notif_count);
        notifCount.setText(String.valueOf(mNotifCount));
        notifCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //List of items to be show in  alert Dialog are stored in array of strings/char sequences

                final String[] items = {"AAAAAA", "BBBBBBB", "CCCCCCC", "DDDDDDDD"};


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                //set the title for alert dialog

                builder.setTitle("Choose names: ");


                //set items to alert dialog. i.e. our array , which will be shown as list view in alert dialog

                builder.setItems(items, new DialogInterface.OnClickListener() {


                    @Override

                    public void onClick(DialogInterface dialog, int item) {

                        // setting the button text to the selected itenm from the list

                        //   button.setText(items[item]);

                    }

                });


                //Creating CANCEL button in alert dialog, to dismiss the dialog box when nothing is selected

                builder

                        .setCancelable(false)

                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {


                            @Override

                            public void onClick(DialogInterface dialog, int id) {

                                //When clicked on CANCEL button the dalog will be dismissed

                                dialog.dismiss();

                            }

                        });


                // Creating alert dialog

                AlertDialog alert = builder.create();


                //Showing alert dialog

                alert.show();


            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_drawer) {
            drawerLayout.openDrawer(Gravity.RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap circuleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(circuleBitmap);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return circuleBitmap;
    }

}




package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.home;

import android.app.Activity;
import android.content.Context;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
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
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Notification;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.help.Help;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin.AdminPanelFragment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.signUpAndLogin.LoginActivity;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.signUpAndLogin.SignUpActivity;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.userAccount.userPage;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.Tools;


public class MainActivity extends AppCompatActivity {

    private Boolean firstTime = null;
    private String[] drawerItemsTitles, notificationsText;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String mTitle;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user,tempUser;
    private Button notifCount;
    private int mNotifCount = 0;
    private ArrayList<Notification> notifications;
    private Notification notif;

    static Activity thisActivity;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, homeFragment)
                    .commit();


        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }
    }


    private void selectItem(int position) {
        // Create a new fragment
        Fragment fragment = null;
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
            case 4:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                break;
            default:
                fragment = new HomeFragment();
                mTitle = getString(R.string.sandoogh);
        }
        if (mTitle != null || fragment != null) {
            getSupportActionBar().setTitle(mTitle);
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).addToBackStack(null)
                    .commit();
            drawerList.setItemChecked(position, true);
            setTitle(drawerItemsTitles[position]);
        }

        // Highlight the selected item, update the title, and close the drawer
        drawerLayout.closeDrawer(drawerList);
    }


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
            mDatabase.child("Users").child(user.getUid()).child("image").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (!snapshot.getValue().toString().equals("0")) {
                        String base64Image = (String) snapshot.getValue();
                        byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                        imageView.setImageBitmap(
                                BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                        );

                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        imageView.setImageBitmap(getCircleBitmap(bitmap));

                    } else {
                        imageView.setImageDrawable(getAnonymousDrawable());
                    }

                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
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
        } catch (RuntimeException e) {
            Log.e("R", "Error in drawer database function " + e);
            Toast.makeText(this, R.string.Error, Toast.LENGTH_SHORT).show();
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
        final PopupWindow pop = popupWindowNotif();
        notifCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop.update(0, 0, 250, WindowManager.LayoutParams.WRAP_CONTENT);
                int[] xy = new int[2];
                v.getLocationInWindow(xy);
                pop.showAtLocation(v, Gravity.NO_GRAVITY, xy[0] , xy[1] + v.getHeight() );
                //pop.showAsDropDown(v, -5, 0);
                // listViewNotif.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                //         View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                //Log.e("R", "" + pop.getWidth() + "  " + listViewNotif.getWidth());

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public PopupWindow popupWindowNotif() {
        PopupWindow popupWindow = new PopupWindow(this);
        final ListView listViewNotif = new ListView(this);
        tempUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("Users").child(tempUser.getUid())
                .child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNotifCount = 0;
                List<DataSnapshot> list = Tools.iteratorToList(dataSnapshot.getChildren().iterator());
                DataSnapshot[] notificationDataSnapshots = list.toArray(new DataSnapshot[list.size()]);
                notifications = new ArrayList<>();
                notificationsText = new String[notificationDataSnapshots.length];
                for (DataSnapshot notificationDataSnapshot : notificationDataSnapshots) {
                    notif = notificationDataSnapshot.getValue(Notification.class);
                    notif.setId(notificationDataSnapshot.getKey());
                    if (notif.getType().equals("invite")){
                        calNotifInvite();
                    } else if (notif.getType().equals("payment")){
                        calNotifPayment();
                    }
                }
                notifCount.setText(String.valueOf(mNotifCount));
                NotificationAdapter adapter = new NotificationAdapter(thisActivity, notificationsText, notifications);
                listViewNotif.setAdapter(adapter);
            }

            private void calNotifInvite() {
                if (notif.getState().equals("pending")) {
                    notifications.add(notif);
                    notificationsText[mNotifCount] = notif.getSandooghName();
                    mNotifCount++;
                }else if (notif.getState().equals("accepted")){
                    mDatabase.child("sandooghs").child(notif.getSandooghName()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //                   List<DataSnapshot> list = Tools.iteratorToList(dataSnapshot.getChildren().iterator());
                            //                 DataSnapshot[] membersDataSnapshots = list.toArray(new DataSnapshot[list.size()]);
                            ArrayList<String> memberIds = new ArrayList<>();
//                            for (DataSnapshot member : membersDataSnapshots) {
//                                memberIds.add(member.getValue(String.class));
//                            }
                            Sandoogh sandoogh = dataSnapshot.getValue(Sandoogh.class);
                            memberIds = sandoogh.getMemberIds();
                            memberIds.add(tempUser.getUid());
                            sandoogh.setMemberIds(memberIds);
                            sandoogh.addNewMemberPayments(tempUser.getUid());
                            mDatabase.child("sandooghs").child(notif.getSandooghName()).setValue(sandoogh);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabase.child("sandooghs").child(notif.getSandooghName()).child("pendingMembersIds").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<DataSnapshot> list = Tools.iteratorToList(dataSnapshot.getChildren().iterator());
                            DataSnapshot[] membersDataSnapshots = list.toArray(new DataSnapshot[list.size()]);
                            ArrayList<String> memberIds = new ArrayList<>();
                            for (DataSnapshot member : membersDataSnapshots) {
                                memberIds.add(member.getValue(String.class));
                            }
                            memberIds.remove(tempUser.getUid());
                            mDatabase.child("sandooghs").child(notif.getSandooghName()).child("pendingMembersIds").setValue(memberIds);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabase.child("Users").child(tempUser.getUid()).child("notifications").child(notif.getId()).removeValue();
                }else if (notif.getState().equals("rejected")){
                    mDatabase.child("sandooghs").child(notif.getSandooghName()).child("pendingMembersIds").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<DataSnapshot> list = Tools.iteratorToList(dataSnapshot.getChildren().iterator());
                            DataSnapshot[] membersDataSnapshots = list.toArray(new DataSnapshot[list.size()]);
                            ArrayList<String> memberIds = new ArrayList<>();
                            for (DataSnapshot member : membersDataSnapshots) {
                                memberIds.add(member.getValue(String.class));
                            }
                            memberIds.remove(tempUser.getUid());
                            mDatabase.child("sandooghs").child(notif.getSandooghName()).child("pendingMembersIds").setValue(memberIds);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabase.child("Users").child(tempUser.getUid()).child("notifications").child(notif.getId()).removeValue();
                }
            }
            private void calNotifPayment() {
                if (notif.getState().equals("pending")) {
                    notifications.add(notif);
                    notificationsText[mNotifCount] = notif.getSandooghName();
                    mNotifCount++;
                }else if (notif.getState().equals("delete")){
                    mDatabase.child("Users").child(tempUser.getUid()).child("notifications").child(notif.getId()).removeValue();
                    final FragmentManager fragmentManager = getSupportFragmentManager();
                    mDatabase.child("sandooghs").child(notif.getSandooghName()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Sandoogh sandoogh = dataSnapshot.getValue(Sandoogh.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("SANDOOGH", sandoogh);
                            AdminPanelFragment adminFragment = new AdminPanelFragment();
                            adminFragment.setArguments(bundle);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, adminFragment).addToBackStack(null).addToBackStack(null)
                                    .commit();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewNotif.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        popupWindow.setFocusable(true);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(listViewNotif);
        return popupWindow;
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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}




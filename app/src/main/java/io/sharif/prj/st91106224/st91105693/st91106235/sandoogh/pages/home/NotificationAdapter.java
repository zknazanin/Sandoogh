package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Notification;


class NotificationAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] values;
    private TextView notifText;
    private ImageView accept, reject;
    private ArrayList<Notification> notifications;
    private Notification notification;
    private FirebaseDatabase dataBase;

    public NotificationAdapter(Context context, String[] values,ArrayList<Notification> notifications) {
        super(context, R.layout.notif_list, values);
        this.values = values;
        this.context = context;
        this.notifications = notifications;
        dataBase = FirebaseDatabase.getInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        notification = notifications.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.notif_list, parent, false);
        notifText = (TextView) rowView.findViewById(R.id.notifText);
        notifText.setText(notifText.getText() + " \n" + notification.getSandooghName());
        accept = (ImageView) rowView.findViewById(R.id.accept);
        reject = (ImageView) rowView.findViewById(R.id.reject);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification = notifications.get(position);
                notification.setState("accepted");
                dataBase.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("notifications")
                        .child(notification.getId()).setValue(notification);
                List<String> list = new ArrayList<String>(Arrays.asList(values));
                list.remove(getItem(position));
                notifications.remove(position);
                values = list.toArray(new String[list.size()]);
                notifyDataSetChanged();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification = notifications.get(position);
                notification.setState("rejected");
                dataBase.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("notifications")
                        .child(notification.getId()).setValue(notification);
                List<String> list = new ArrayList<String>(Arrays.asList(values));
                list.remove(getItem(position));
                notifications.remove(position);
                values = list.toArray(new String[list.size()]);
                notifyDataSetChanged();
            }
        });
        return rowView;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }
}
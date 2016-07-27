package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Notification;


class NotificationAdapter extends ArrayAdapter<String> {
    private Context context;
    private TextView notifText;
    private ImageView accept, reject;
    ArrayList<Notification> notifications;
    private Notification notification;

    public NotificationAdapter(Context context, String[] values,ArrayList<Notification> notifications) {
        super(context, R.layout.notif_list, values);
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        notification = notifications.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.notif_list, parent, false);
        notifText = (TextView) rowView.findViewById(R.id.notifText);
        notifText.setText(notifText.getText() + " \n" + notification.getSandooghName());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.setState("accepted");
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.setState("rejected");
            }
        });
        return rowView;
    }
}
package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

public class UserAdapter extends ArrayAdapter<User> {

    String adminId;

    public UserAdapter(Context c, List<User> items, String adminId) {
        super(c, 0, items);
        this.adminId = adminId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserView userView = (UserView) convertView;

        if (null == userView) {
            userView = UserView.inflate(parent);
        }

        userView.setItem(getItem(position), adminId);
        return userView;
    }
}

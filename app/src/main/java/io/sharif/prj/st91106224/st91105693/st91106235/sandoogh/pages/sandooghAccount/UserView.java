package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

public class UserView extends RelativeLayout {

    private TextView usernameTextView;

    public UserView(Context c) {
        this(c, null);
    }

    public UserView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.user_view_children, this, true);
        setupChildren();
    }

    public static UserView inflate(ViewGroup parent) {
        return (UserView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_view, parent, false);
    }

    private void setupChildren() {
        usernameTextView = (TextView) findViewById(R.id.username);
    }

    public void setItem(User user) {
        usernameTextView.setText(user.getUsername());
    }
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.Admin;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

public class UserView extends RelativeLayout {

    User user;

    private RelativeLayout mainLayout;
    private TextView usernameTextView;
    private ImageView deleteImageView;


    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }


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
        deleteImageView = (ImageView) findViewById(R.id.delete_image_view);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
    }

    public void setItem(User user, String adminId) {

        this.user = user;

        usernameTextView.setText(user.getUsername());

        if (user.getId().equals(adminId)) {
            deleteImageView.setVisibility(INVISIBLE);
        } else {
            deleteImageView.setVisibility(VISIBLE);
            deleteImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!toDelete) {
                        toDelete = true;
                        mainLayout.setBackgroundResource(R.drawable.rounded_corner_shape_delete);
                    } else {
                        toDelete = false;
                        mainLayout.setBackgroundResource(R.drawable.rounded_corner_shape_background_color);
                    }
                }
            });
        }

    }
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount.User;

/**
 * Created by foroughM on 7/17/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;

public class UserViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView username;
    private ImageView userPhoto;

    public UserViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        username = (TextView) itemView.findViewById(R.id.user_name);
        userPhoto = (ImageView) itemView.findViewById(R.id.user_photo);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }

    public ImageView getUserPhoto() {
        return userPhoto;
    }

    public TextView getUsername() {
        return username;
    }
}
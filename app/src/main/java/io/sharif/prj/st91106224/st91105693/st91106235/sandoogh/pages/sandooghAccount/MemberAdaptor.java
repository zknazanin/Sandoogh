package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

public class MemberAdaptor  extends RecyclerView.Adapter<UserViewHolders> {

  private List<User> itemList;
  private Context context;

  public MemberAdaptor(Context context, List<User> itemList) {
   this.itemList = itemList;
   this.context = context;
  }

  @Override
  public UserViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

   View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, null);
   UserViewHolders rcv = new UserViewHolders(layoutView);
   return rcv;
  }

  @Override
  public void onBindViewHolder(UserViewHolders holder, int position) {
   holder.username.setText(itemList.get(position).getUsername());
      String image = itemList.get(position).getImage();
      if (!image.equals("0")) {
          byte[] imageAsBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
          holder.userPhoto.setImageBitmap(
                  BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
          );
      }
  }

  @Override
  public int getItemCount() {
   return this.itemList.size();
  }
 }
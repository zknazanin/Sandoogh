package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
   holder.userPhoto.setImageResource(itemList.get(position).getImage());
  }

  @Override
  public int getItemCount() {
   return this.itemList.size();
  }
 }
package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.User;

/**
 * Created by foroughM on 7/17/2016.
 */
public class Members extends Fragment{
    private  ViewGroup view;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (ViewGroup) inflater.inflate(R.layout.members, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<User> gaggeredList = getListItemData();

        MemberAdaptor rcAdapter = new MemberAdaptor(getContext(), gaggeredList);
        recyclerView.setAdapter(rcAdapter);
        return view;
    }
    private List<User> getListItemData(){
        List<User> listViewItems = new ArrayList<User>();
        listViewItems.add(new User("Alkane", R.mipmap.ic_add_circle_black_24dp));
        listViewItems.add(new User("Ethane", R.mipmap.ic_chevron_left_black_24dp));
        listViewItems.add(new User("Alkyne", R.mipmap.ic_chevron_right_black_24dp));
        listViewItems.add(new User("Benzene", R.mipmap.ic_highlight_off_black_24dp));
        listViewItems.add(new User("Amide", R.mipmap.ic_launcher));
        listViewItems.add(new User("Amino Acid", R.mipmap.ic_more_vert_black_24dp));
        listViewItems.add(new User("Phenol", R.mipmap.ic_add_circle_black_24dp));
        listViewItems.add(new User("Carbonxylic", R.mipmap.ic_chevron_left_black_24dp));
        listViewItems.add(new User("Nitril", R.mipmap.ic_chevron_right_black_24dp));
        listViewItems.add(new User("Ether", R.mipmap.ic_highlight_off_black_24dp));
        listViewItems.add(new User("Ester", R.mipmap.ic_launcher));
        listViewItems.add(new User("Alcohol", R.mipmap.ic_more_vert_black_24dp));

        return listViewItems;
    }
}
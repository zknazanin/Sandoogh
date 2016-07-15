package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandoogh_account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;

/**
 * Created by foroughM on 7/15/2016.
 */
public class san_account extends Fragment {
    ImageView menu;
    ViewGroup view;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.san_account, container, false);
//        setHasOptionsMenu(true);
//        getOverflowMenu();
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.showOverflowMenu();
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        menu = (ImageView) view.findViewById(R.id.popup);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), menu);
                popup.getMenuInflater()
                        .inflate(R.menu.san_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals(getString(R.string.san_account_edit))){

                        }else if (item.getTitle().equals(getString(R.string.san_account_amount))){

                        } else {
                            // show member
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
        return view;
    }
    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(getContext());
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.add("add");
    }
}

package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.sandooghAccount;

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
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;


public class SandooghAccountFragment extends Fragment {
    ImageView menu;
    ViewGroup view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        Sandoogh sandoogh = (Sandoogh) bundle.getSerializable("SELECTED_SANDOOGH");

        if (sandoogh.getType().equals("A")) {
            view = (ViewGroup) inflater.inflate(R.layout.san_account_a, container, false);
        } else {
            view = (ViewGroup) inflater.inflate(R.layout.san_account_b, container, false);

        }


        createMenu();
        return view;
    }

    private void createMenu() {
        menu = (ImageView) view.findViewById(R.id.popup);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), menu);
                popup.getMenuInflater()
                        .inflate(R.menu.san_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals(getString(R.string.san_account_edit))) {

                        } else if (item.getTitle().equals(getString(R.string.san_account_amount))) {

                        } else {
                            // show member
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
    }
}

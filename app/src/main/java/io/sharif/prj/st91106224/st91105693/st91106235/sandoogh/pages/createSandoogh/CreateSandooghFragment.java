package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;

public class CreateSandooghFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.create_sandoogh_fragment, container, false);

        createTabs(view);
        return view;
    }

    private void createTabs(View view) {

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        final TabLayout.Tab type = tabLayout.newTab();
        final TabLayout.Tab info = tabLayout.newTab();
        final TabLayout.Tab members = tabLayout.newTab();
        final TabLayout.Tab confirm = tabLayout.newTab();

        type.setText(R.string.type_tab_title);
        info.setText(R.string.info_tab_title);
        members.setText(R.string.members_tab_title);
        confirm.setText(R.string.confirm_tab_title);

        tabLayout.addTab(type, 0);
        tabLayout.addTab(info, 1);
        tabLayout.addTab(members, 2);
        tabLayout.addTab(confirm, 3);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getActivity(), R.color.colorAccent));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
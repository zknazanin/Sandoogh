package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SandooghTypeFragment();
            default:
                return new TabFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
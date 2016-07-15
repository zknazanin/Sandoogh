package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private SandooghTypeFragment sandooghTypeFragment;
    private SandooghDescriptionFragment sandooghDescriptionFragment;
    private SandooghInviteFragment sandooghInviteFragment;
    private SandooghConfirmFragment sandooghConfirmFragment;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        sandooghTypeFragment = new SandooghTypeFragment();
        sandooghDescriptionFragment = new SandooghDescriptionFragment();
        sandooghInviteFragment = new SandooghInviteFragment();
        sandooghConfirmFragment = new SandooghConfirmFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return sandooghTypeFragment;
            case 1:
                return sandooghDescriptionFragment;
            case 2:
                return sandooghInviteFragment;
            case 3:
                return sandooghConfirmFragment;
            default:
                return new TabFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    public void createSandoogh() {

        Sandoogh sandoogh = new Sandoogh();

        sandoogh.setAdminUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        sandoogh.setTotal(0);
        sandoogh.setPeriodPay(200);
        sandoogh.setStartDate(new Date());

        // Sandoogh type fragment
        sandoogh.setType(sandooghTypeFragment.getSandooghType());

        // Sandoogh description fragment
        sandoogh.setAccountNum(sandooghDescriptionFragment.getAccountNum());
        sandoogh.setCardNum(sandooghDescriptionFragment.getCardNum());
        sandoogh.setName(sandooghDescriptionFragment.getName());
        sandoogh.setPeriod(sandooghDescriptionFragment.getPeriod());

        // Sandoogh invite fragment
        // private User[] members;
        // TODO: 7/15/2016

        Database.getInstance().saveSandoogh(sandoogh);

    }


}
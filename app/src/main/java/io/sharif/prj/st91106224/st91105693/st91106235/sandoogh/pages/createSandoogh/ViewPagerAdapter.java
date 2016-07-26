package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.R;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.data.Sandoogh;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.home.HomeFragment;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.serverConnection.Database;
import io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.tools.SolarCalendar;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private SandooghTypeFragment sandooghTypeFragment;
    private SandooghDescriptionFragment sandooghDescriptionFragment;
    private SandooghInviteFragment sandooghInviteFragment;
    private SandooghConfirmFragment sandooghConfirmFragment;
    private String sandooghType, sandooghName;
    private Boolean type = false, name = false;

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

    public void createSandoogh(Activity activity, FragmentManager fragmentManager) {

        Sandoogh sandoogh = new Sandoogh();

        sandoogh.setAdminUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        sandoogh.setTotal(0);
        sandoogh.setPeriodPay(sandooghDescriptionFragment.getPeriodPay());
        sandoogh.setStartDate(new SolarCalendar());

        // Sandoogh type fragment
        sandooghType = sandooghTypeFragment.getSandooghType();
        if (!sandooghType.equals("none")) {
            sandoogh.setType(sandooghType);
            type = true;
        }else {
            Toast.makeText(activity, R.string.typeError,
                    Toast.LENGTH_LONG).show();
        }

        // Sandoogh description fragment
        sandooghName = sandooghDescriptionFragment.getName();
        if(!sandooghName.equals("")){
            sandoogh.setName(sandooghName);
            name = true;
        } else {
            Toast.makeText(activity, R.string.nameError,
                    Toast.LENGTH_LONG).show();
        }
        sandoogh.setAccountNum(sandooghDescriptionFragment.getAccountNum());
        sandoogh.setCardNum(sandooghDescriptionFragment.getCardNum());
        sandoogh.setPeriod(sandooghDescriptionFragment.getPeriod());

        ArrayList<String> memberIds = new ArrayList<>();
        for (int i=0 ; i<sandooghInviteFragment.getMemberIds().size(); i++)
            memberIds.add(sandooghInviteFragment.getMemberIds().get(i));
        memberIds.add(sandoogh.getAdminUid());
        sandoogh.setMemberIds(memberIds);

        sandoogh.calculateAndAddNextPayment(sandoogh.getStartDate());
        if(type && name) {
            Database.getInstance().saveSandoogh(sandoogh);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new HomeFragment())
                    .commit();
            Toast.makeText(activity, R.string.success,
                    Toast.LENGTH_LONG).show();
        }

    }
}
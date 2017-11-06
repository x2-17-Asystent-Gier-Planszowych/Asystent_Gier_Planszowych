package pwcdma.asystentgierplanszowych.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pwcdma.asystentgierplanszowych.fragment.GamesFragment;
import pwcdma.asystentgierplanszowych.fragment.GroupsFragment;
import pwcdma.asystentgierplanszowych.fragment.ProfileFragment;
import pwcdma.asystentgierplanszowych.fragment.DiceFragment;
import pwcdma.asystentgierplanszowych.fragment.TimerFragment;
import pwcdma.asystentgierplanszowych.fragment.ToolsFragment;


public class MainActivityViewPagerAdapter extends FragmentPagerAdapter {

    public MainActivityViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return new GroupsFragment();
            case 1:
                return new GamesFragment();
            case 2:
                return new ToolsFragment();
            case 3:
                return new ProfileFragment();
        }
        return new ProfileFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}

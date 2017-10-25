package pwcdma.asystentgierplanszowych.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pwcdma.asystentgierplanszowych.fragment.GamesFragment;
import pwcdma.asystentgierplanszowych.fragment.GroupsFragment;
import pwcdma.asystentgierplanszowych.fragment.ProfileFragment;
import pwcdma.asystentgierplanszowych.fragment.TimerFragment;

public class MainActivityViewPagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_OF_FRAGMENTS = 4;
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
                return new TimerFragment();
            case 3:
                return new ProfileFragment();
        }
        return new ProfileFragment();
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;
    }
}

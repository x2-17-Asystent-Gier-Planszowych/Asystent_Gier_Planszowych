package pwcdma.asystentgierplanszowych.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import pwcdma.asystentgierplanszowych.fragment.StartFragment;
import pwcdma.asystentgierplanszowych.model.StartViewPagerItem;


public class StartFragmentViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = StartFragmentViewPagerAdapter.class.getSimpleName();
    
    private ArrayList<StartViewPagerItem> viewPagerItems;

    public StartFragmentViewPagerAdapter(FragmentManager fm, ArrayList<StartViewPagerItem> fragments) {
        super(fm);
        Log.d(TAG, "StartFragmentViewPagerAdapter: ");
        this.viewPagerItems = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: ");
        StartViewPagerItem item = viewPagerItems.get(position);
        return StartFragment.newInstance(item);
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: ");
        return this.viewPagerItems.size();
    }

}
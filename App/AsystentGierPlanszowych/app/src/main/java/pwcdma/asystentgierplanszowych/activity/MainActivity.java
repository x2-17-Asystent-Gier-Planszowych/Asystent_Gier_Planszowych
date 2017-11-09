package pwcdma.asystentgierplanszowych.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.adapter.MainActivityViewPagerAdapter;
import pwcdma.asystentgierplanszowych.model.CustomViewPager;

public class MainActivity extends AppCompatActivity {
    // TODO: 23.10.2017  ponaprawiać
    private final String TAG = MainActivity.class.getSimpleName();

    private static final String TAB = "pwcdma.asystentgierplanszowych.tab";
    private CustomViewPager mVpFragmentPager;
    private MainActivityViewPagerAdapter mPagerAdapter;
    private TabLayout mTlNavBar;
    private int[] mNavBarIcons = {
            R.drawable.bottom_navigation_bar_group_selector,
            R.drawable.bottom_navigation_bar_games_selector,
            R.drawable.bottom_navigation_bar_tools_selector,
            R.drawable.bottom_navigation_bar_profile_selector};
    private String[] mNavBarTexts = {
            "Grupy",
            "Gry",
            "Przybornik",
            "Profil"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setViewPager();
        setTabLayout();
        setFragmentBarIcons();
        if (savedInstanceState != null) {
            TabLayout.Tab tab = mTlNavBar.getTabAt(savedInstanceState.getInt(TAB));
            tab.select();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(state);
        state.putInt(TAB, mTlNavBar.getSelectedTabPosition());
    }

    private void findViews() {
        Log.d(TAG, "findViews: ");
        mTlNavBar = (TabLayout) findViewById(R.id.mainActivityTabLayout);
        mVpFragmentPager = (CustomViewPager) findViewById(R.id.mainActivityViewPager);
    }

    private void setViewPager() {
        Log.d(TAG, "setViewPager: ");
        mPagerAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
        mVpFragmentPager.setPagingEnabled(false);
        mVpFragmentPager.setAdapter(mPagerAdapter);
    }

    private void setTabLayout() {
        Log.d(TAG, "setTabLayout: ");
        mTlNavBar.setupWithViewPager(mVpFragmentPager);
    }


    private void setFragmentBarIcons() {
        Log.d(TAG, "setFragmentBarIcons: ");
        for (int i = 0; i < mNavBarIcons.length; i++) {
            mTlNavBar.getTabAt(i).setIcon(mNavBarIcons[i]);
            mTlNavBar.getTabAt(i).setText(mNavBarTexts[i]);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

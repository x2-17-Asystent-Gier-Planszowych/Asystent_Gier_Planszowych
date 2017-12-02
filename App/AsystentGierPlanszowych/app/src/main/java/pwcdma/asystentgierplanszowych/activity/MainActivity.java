package pwcdma.asystentgierplanszowych.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.adapter.MainActivityViewPagerAdapter;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.fragment.GamesFragment;
import pwcdma.asystentgierplanszowych.model.CustomViewPager;
import pwcdma.asystentgierplanszowych.model.Game;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

public class MainActivity extends AppCompatActivity {
    // TODO: 23.10.2017  ponaprawiać
    private final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progress;
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
      /*  String json = load(getApplicationContext());
        Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
        List<Game> gamesListFromServer = new Gson().fromJson(json, listType);
        for(Game g : gamesListFromServer){
            Content.Item item = new Content.Item(g.getId().toString(),g.getName(),"");
            Content.addGame(item);
        }*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
        if (savedInstanceState != null) {
            TabLayout.Tab tab = mTlNavBar.getTabAt(savedInstanceState.getInt(TAB));
            tab.select();
        }
    }


    public String load(Context context){
      /*  try {
            FileInputStream fis = context.openFileInput(getFilesDir().getAbsolutePath() + "/" + "data" + ".txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(fis));
            String s = "";
            String txt = "";
            while ((s = r.readLine()) != null) {
                txt += s;
            }
            r.close();
            Toast.makeText(MainActivity.this, txt, Toast.LENGTH_LONG).show();
            return txt;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/
      return null;
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
        addOnPageChangeListenerToAdapter();
        mVpFragmentPager.setOffscreenPageLimit(3);
    }

    private void addOnPageChangeListenerToAdapter(){
        mVpFragmentPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: " + state);
            }
        });
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

    public void kurwa(View view){
        Intent myIntent = new Intent(MainActivity.this, AddGameActivity.class);
        MainActivity.this.startActivity(myIntent);

    }

    class UserAddToGroup extends AsyncTask<Void, Void, Boolean> {


        private Context cont;
        UserAddToGroup(String email, String password,Context context) {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
                //ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/signin?" +
                 //       "login=" + mLogin + "&haslo=" + hashPassword);
               // String response = connection.getResponse();



                return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }

    }
}

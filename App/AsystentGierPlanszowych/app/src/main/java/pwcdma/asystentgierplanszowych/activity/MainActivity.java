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
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.adapter.MainActivityViewPagerAdapter;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.CustomViewPager;
import pwcdma.asystentgierplanszowych.model.Game;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.model.User;
import pwcdma.asystentgierplanszowych.model.UserInGroup;
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
    private LinearLayout tab_host;
    private UserAddToGroup userAddToGroup;
    private View mProgressView;
    private WaitFroDate waitFroDate;
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
        tab_host =(LinearLayout) findViewById(R.id.tab_host);
        mProgressView = findViewById(R.id.login_progress2);
     new Thread(new Runnable() {
            public Handler mHandler;
            @Override
            public void run() {
                while(true) {
                    try {
                    userAddToGroup = new UserAddToGroup();
                    userAddToGroup.execute((Void) null);
                    Thread.sleep(3000);
                    userAddToGroup.cancel(true);
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
        showProgress(true);
        waitFroDate = new WaitFroDate();
        waitFroDate.execute((Void) null);
        Button a = new Button(this);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        tab_host.setVisibility(show ? View.GONE : View.VISIBLE);
        tab_host.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tab_host.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
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

                UsefullValues.pageSelected = position;
                Log.d(TAG, "onPageSelected: " +  UsefullValues.pageSelected);
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
        /* TODO */
        if(UsefullValues.pageSelected == 1) {
            Intent myIntent = new Intent(MainActivity.this, AddGameActivity.class);
            MainActivity.this.startActivity(myIntent);
        }else if(UsefullValues.pageSelected == 0){
            Intent myIntent = new Intent(MainActivity.this, AddGroupActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
    }


    public void onClickButtonOnListGroup(View view){
 /*      // Content.GROUPS.bsetOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                // Your code for item clicks
            }
        });*/
    }
    public void refresh(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        showProgress(true);
        waitFroDate = new WaitFroDate();
        waitFroDate.execute((Void) null);
    }

    class WaitFroDate extends AsyncTask<Void, Void, Boolean> {

        WaitFroDate(){}



        @Override
        protected Boolean doInBackground(Void... params) {

            gamesGet();
            groupGet();
            userGet();
            return true;
        }


        protected void gamesGet() {

            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/games");
            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Content.clearList(Content.GAMES, Content.GAME_MAP);
            Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
            List<Game> gamesListFromServer = new Gson().fromJson(responsee, listType);
            int i = 0;
            for(Game g : gamesListFromServer){
                i++;
                Content.Item item = new Content.Item(String.valueOf(i),g.getName(),"",null);
                Content.addGame(item);
            }
        }

        protected void userGet() {

            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/get/users");
            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> userListFromSerwer = new Gson().fromJson(responsee, listType);
            for(User g : userListFromSerwer){
                Content.Item item = new Content.Item(g.getId().toString(),g.getUsername(),"",null);
                Content.addUser(item);
            }
        }



        protected void groupGet() {

            GroupControllerSerwer gf = new GroupControllerSerwer();
            String responsee = gf.getAllGroups();


            Type listType = new TypeToken<ArrayList<Group>>(){}.getType();
            List<Group> gamesListFromServer = new Gson().fromJson(responsee, listType);
            Content.clearList(Content.GROUPS, Content.GROUP_MAP);


            int i = 0;
            for(Group g : gamesListFromServer){
                Content.Item item = new Content.Item(String.valueOf(++i), g.getGroupName(),"",null);
                Content.addGroup(item);
            }
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            // mAuthTask = null;
            // showProgress(false);

         //   activity.startActivity(new Intent(activity, MainActivity.class));;
        }

        @Override
        protected void onCancelled() {
            //  showProgress(false);
        }
    }
    class UserAddToGroup extends AsyncTask<Void, Void, Boolean> {

        private String respone="";
        private Context cont;
        UserAddToGroup() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
                ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/group/useringroup");

            try {
                respone = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Type listType = new TypeToken<ArrayList<UserInGroup>>(){}.getType();
            List<UserInGroup> gamesListFromServer = new Gson().fromJson(respone, listType);
            if(gamesListFromServer.size()>Content.sizeOfList){
                for (int i = Content.sizeOfList;i<gamesListFromServer.size();i++
                        ) {
                    if(gamesListFromServer.get(i).getNameUser().equals("misckq")){
                        Content.sizeOfList = gamesListFromServer.size();
                        return true;
                    }

                }
            }

            return false;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            showProgress(false);
            if(success){
            Toast.makeText(MainActivity.this, "Dodano cię do grupy", Toast.LENGTH_LONG).show();}
        }

        @Override
        protected void onCancelled() {

        }

    }
}

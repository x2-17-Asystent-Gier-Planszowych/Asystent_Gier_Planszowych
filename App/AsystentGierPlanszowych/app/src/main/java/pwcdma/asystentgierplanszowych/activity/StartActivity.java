package pwcdma.asystentgierplanszowych.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.adapter.StartFragmentViewPagerAdapter;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.fragment.StartFragment;
import pwcdma.asystentgierplanszowych.model.Game;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.model.StartViewPagerItem;
import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;
import pwcdma.asystentgierplanszowych.server.ServerConnection;
import pwcdma.asystentgierplanszowych.model.User;

public class StartActivity extends AppCompatActivity implements StartFragment.OnFragmentInteractionListener {

    private final String TAG = StartActivity.class.getSimpleName();

    private static final int REQUEST_CODE_LOG_IN = 1;
    private static final int REQUEST_CODE_SIGN_UP = 2;
    private Button signInButton;
    private TextView signUpText;
    private ViewPager mViewPager;
    private TabLayout mTlDotIndicator;
    private StartFragmentViewPagerAdapter mVpAdapter;
    private ArrayList<StartViewPagerItem> mFragmentItemsList = new ArrayList<>();
    private  MainActivityTask mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        if (isUserLoggedIn())
            startMainActivity();
        setContentView(R.layout.activity_start);
        mainActivity = new MainActivityTask(this);
        mainActivity.execute((Void) null);
        findViews();
        setActionBar();
        addFragmentsToList();
        setAdapter();
        setButtons();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_LOG_IN || requestCode == REQUEST_CODE_SIGN_UP)
                && resultCode == LogInActivity.RESULT_CODE_SUCCESS) {
            startMainActivity();
        }
    }

    private void setActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void findViews() {
        Log.d(TAG, "findViews: ");
        signInButton = (Button) findViewById(R.id.sign_in_button);
        mViewPager = (ViewPager) findViewById(R.id.start_view_pager);
        mTlDotIndicator = (TabLayout) findViewById(R.id.start_tab_indicator);
        signUpText = (TextView) findViewById(R.id.sign_up_text);
    }

    private void setAdapter(){
        Log.d(TAG, "setAdapter: ");
        mVpAdapter = new StartFragmentViewPagerAdapter(getSupportFragmentManager(), mFragmentItemsList);
        mViewPager.setAdapter(mVpAdapter);
        mTlDotIndicator.setupWithViewPager(mViewPager, true);
    }

    private void addFragmentsToList(){
        Log.d(TAG, "addFragmentsToList: ");
        mFragmentItemsList.add(new StartViewPagerItem(R.drawable.ic_start_tab_1, getString(R.string.startTabText1)));
        mFragmentItemsList.add(new StartViewPagerItem(R.drawable.ic_start_tab_2, getString(R.string.startTabText2)));
        mFragmentItemsList.add(new StartViewPagerItem(R.drawable.ic_start_tab_3, getString(R.string.startTabText3)));
    }

    private void setButtons() {
        Log.d(TAG, "setButtons: ");
        signInButton.setOnClickListener(onClickListener);
        signUpText.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.sign_in_button:
                    Log.d(TAG, "onClick: signInButton");
                    startActivityForResult(new Intent(StartActivity.this, LogInActivity.class), REQUEST_CODE_LOG_IN);
                    break;
                case R.id.sign_up_text:
                    Log.d(TAG, "onClick: signUpText");
                    startActivityForResult(new Intent(StartActivity.this, SignUpActivity.class), REQUEST_CODE_SIGN_UP);
                    break;
            }
        }
    };

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private boolean isUserLoggedIn(){
        File userDataFile = new File(getFilesDir(), User.FILE_NAME);
        return userDataFile.exists();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction: ");

    }

    class MainActivityTask extends AsyncTask<Void, Void, Boolean> {

        private Activity activity;

        MainActivityTask(Activity activity) {
            this.activity=activity;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            gamesGet();
            groupGet();
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

            Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
            List<Game> gamesListFromServer = new Gson().fromJson(responsee, listType);
            for(Game g : gamesListFromServer){
                Content.Item item = new Content.Item(g.getId().toString(),g.getName(),"");
                Content.addGame(item);
            }
        }



        protected void groupGet() {

            GroupControllerSerwer gf = new GroupControllerSerwer();
            String responsee = gf.getAllGroups();


            Type listType = new TypeToken<ArrayList<Group>>(){}.getType();
            List<Group> gamesListFromServer = new Gson().fromJson(responsee, listType);
            for(Group g : gamesListFromServer){
                Content.Item item = new Content.Item(Integer.toString(g.getId()), g.getGroupName(),"");
                Content.addGroup(item);
            }
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            // mAuthTask = null;
            // showProgress(false);

            //activity.startActivity(new Intent(activity, MainActivity.class));;
        }

        @Override
        protected void onCancelled() {
            //  showProgress(false);
        }
    }

    private void saveUserData(String login, String password){
        /*try {
            JSONObject userDataJson = new JSONObject();
            userDataJson.put("login", login);
            userDataJson.put("password", password);
            String userData = userDataJson.toString();
            File userDataFile = new File(getFilesDir(), "user_data.json");
            FileWriter writer = new FileWriter(userDataFile);
            writer.write(userData);
            writer.close();
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }*/
    }
}

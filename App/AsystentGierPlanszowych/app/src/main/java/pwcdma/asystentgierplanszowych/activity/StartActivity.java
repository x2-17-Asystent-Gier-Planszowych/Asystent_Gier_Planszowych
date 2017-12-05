package pwcdma.asystentgierplanszowych.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.model.UserInGroup;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);

        String loginfromlogin=sp1.getString("loginlogin", null);
        String loginfromsignup=sp1.getString("signuplogin", null);
        // String pass = sp1.getString("Psw", null);

        UsefullValues.name=loginfromsignup;
        UsefullValues.name=loginfromlogin;

        UserAddToGroup userAddToGroup = new UserAddToGroup();
        userAddToGroup.execute((Void) null);

        if (isUserLoggedIn())
            startMainActivity();
        setContentView(R.layout.activity_start);
        findViews();

        setActionBar();
        addFragmentsToList();
        setAdapter();
        setButtons();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
        signInButton = (Button) findViewById(R.id.sign_in_button);
        mViewPager = (ViewPager) findViewById(R.id.start_view_pager);
        mTlDotIndicator = (TabLayout) findViewById(R.id.start_tab_indicator);
        signUpText = (TextView) findViewById(R.id.sign_up_text);
    }

    private void setAdapter(){
        mVpAdapter = new StartFragmentViewPagerAdapter(getSupportFragmentManager(), mFragmentItemsList);
        mViewPager.setAdapter(mVpAdapter);
        mTlDotIndicator.setupWithViewPager(mViewPager, true);
    }

    private void addFragmentsToList(){
        mFragmentItemsList.add(new StartViewPagerItem(R.drawable.ic_start_tab_1, getString(R.string.startTabText1)));
        mFragmentItemsList.add(new StartViewPagerItem(R.drawable.ic_start_tab_2, getString(R.string.startTabText2)));
        mFragmentItemsList.add(new StartViewPagerItem(R.drawable.ic_start_tab_3, getString(R.string.startTabText3)));
    }

    private void setButtons() {
        signInButton.setOnClickListener(onClickListener);
        signUpText.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.sign_in_button:
                    startActivityForResult(new Intent(StartActivity.this, LogInActivity.class), REQUEST_CODE_LOG_IN);
                    break;
                case R.id.sign_up_text:
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
        File userDataFile = new File(getFilesDir(), "user_data.json");
        return userDataFile.exists();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class UserAddToGroup extends AsyncTask<Void, Void, Boolean> {

        private String respone="";
        private Context cont;
        UserAddToGroup() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/group/useringroup");
//
//            try {
//                respone = connection.getResponse();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Type listType = new TypeToken<ArrayList<UserInGroup>>(){}.getType();
//            List<UserInGroup> gamesListFromServer = new Gson().fromJson(respone, listType);
//            Content.sizeOfList = gamesListFromServer.size();
//
               return false;

        }


    }
}

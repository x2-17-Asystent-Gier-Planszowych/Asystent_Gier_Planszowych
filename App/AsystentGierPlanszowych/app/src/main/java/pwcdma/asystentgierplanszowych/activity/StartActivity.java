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

        if (isUserLoggedIn())
            startMainActivity();
        setContentView(R.layout.activity_start);
        getNameUser();
        findViews();
        setActionBar();
        addFragmentsToList();
        setAdapter();
        setButtons();
    }

    private void getNameUser(){
        StringBuilder contents = new StringBuilder();

        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            BufferedReader input =  new BufferedReader(new FileReader(getFilesDir().toString()+ "user_data.json"));
            try {
                String line = null; //not declared within while loop
        /*
        * readLine is a bit quirky :
        * it returns the content of a line MINUS the newline.
        * it returns null only for the END of the stream.
        * it returns an empty String if two newlines appear in a row.
        */
                while (( line = input.readLine()) != null){
                    contents.append(line);
                }
            }
            finally {
                input.close();
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        UsefullValues.name=contents.toString();
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
        File userDataFile = new File(getFilesDir(), "user_data.json");
        return userDataFile.exists();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction: ");

    }


}

package pwcdma.asystentgierplanszowych.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pwcdma.asystentgierplanszowych.Adapter.LogInActivity;
import pwcdma.asystentgierplanszowych.Adapter.StartFragmentViewPagerAdapter;
import pwcdma.asystentgierplanszowych.Fragment.StartFragment;
import pwcdma.asystentgierplanszowych.Model.StartViewPagerItem;
import pwcdma.asystentgierplanszowych.R;

public class StartActivity extends AppCompatActivity implements StartFragment.OnFragmentInteractionListener {

    private final String TAG = StartActivity.class.getSimpleName();

    private static final int REQUEST_CODE_LOG_IN = 1;
    private static final int REQUEST_CODE_SIGN_UP = 2;
    private Button signInButton, signInFacebookButton;
    private TextView signUpText;
    private ViewPager mViewPager;
    private TabLayout mTlDotIndicator;
    private StartFragmentViewPagerAdapter mVpAdapter;
    private ArrayList<StartViewPagerItem> mFragmentItemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViews();
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
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void findViews() {
        Log.d(TAG, "findViews: ");
        signInButton = (Button) findViewById(R.id.sign_in_button);
        signInFacebookButton = (Button) findViewById(R.id.sign_in_facebook_button);
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


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction: ");

    }
}

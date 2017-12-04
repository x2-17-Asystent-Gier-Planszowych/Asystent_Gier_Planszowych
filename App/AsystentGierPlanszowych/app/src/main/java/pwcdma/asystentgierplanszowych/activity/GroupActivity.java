package pwcdma.asystentgierplanszowych.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.Game;
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.server.GamesController;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

/**
 * Created by kriscool on 03.12.2017.
 */

public class GroupActivity extends AppCompatActivity {
    private TextView group_label;
    private TextView game_rand;
    private TextView gamers;
    private  String value;
    private View mProgressView;
    private LinearLayout linearLayout;
    private GetInfo getInfo;
    private boolean isFinished=false;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        group_label = (TextView) findViewById(R.id.group_label);
        game_rand = (TextView) findViewById(R.id.Game_rand);
        gamers = (TextView) findViewById(R.id.gamers);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("nameGroup");
        }

        mProgressView = findViewById(R.id.login_progress3);
        linearLayout = (LinearLayout) findViewById(R.id.tab);

        if(isFinished) {
            showProgress(true);
            getInfo = new GetInfo(value);
            getInfo.execute((Void) null);
        }else{
            showProgress(false);
        }
        group_label.setText("Grupa \n" + value);

            for (String s : UsefullValues.userInGroup) {
                gamers.setText(s + " ,");
            }

    }

    public void addUserToGroup(View view){
        Intent myIntent = new Intent(GroupActivity.this, AddUserToGroupActivity.class);
        myIntent.putExtra("nameGroup", value);
        GroupActivity.this.startActivity(myIntent);
    }
    public void randGame(View view) {
        Random r = new Random();
        int i1 = r.nextInt(Content.GAMES.size() - 1) + 1;
        game_rand.setText(Content.GAMES.get(i1).getContent());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        linearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        linearLayout.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                linearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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

    class GetInfo extends AsyncTask<Void, Void, Boolean> {

        private final String title;
        GetInfo(String title) {
            this.title=title;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/user/group/name?name=" + title);
            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            UsefullValues.userInGroup = new Gson().fromJson(responsee, listType);


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            isFinished=true;
            getInfo=null;


        }
    }
}

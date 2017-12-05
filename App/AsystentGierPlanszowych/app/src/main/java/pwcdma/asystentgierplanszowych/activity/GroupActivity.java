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
import android.widget.EditText;
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
import java.util.StringTokenizer;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.Game;
import pwcdma.asystentgierplanszowych.model.Test;
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.model.User;
import pwcdma.asystentgierplanszowych.server.GamesController;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

/**
 * Created by kriscool on 03.12.2017.
 */

public class GroupActivity extends AppCompatActivity {
    private TextView group_label;
    private TextView game_rand;
    private TextView gamers;
    private EditText about;
    private  String value;
    private View mProgressView;
    private LinearLayout linearLayout;
    private LinearLayout layout;
    private GetInfo getInfo;
    private boolean isFinished=false;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        group_label = (TextView) findViewById(R.id.group_label);
        game_rand = (TextView) findViewById(R.id.Game_rand);
        gamers = (TextView) findViewById(R.id.gamers);
        layout = (LinearLayout) findViewById(R.id.layout);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("nameGroup");
        }
        UsefullValues.group=value;

        mProgressView = findViewById(R.id.login_progress3);
        linearLayout = (LinearLayout) findViewById(R.id.tab);
        SetAbout setAbout = new SetAbout(value);
        setAbout.execute((Void) null);

        showProgress(true);
       // getInfo = new GetInfo(value);
       // getInfo.execute((Void) null);
        Toast.makeText(GroupActivity.this,  UsefullValues.group, Toast.LENGTH_SHORT).show();
        int indexOfGroup=0;
        group_label.setText("Grupa \n" + value);
        for(int i =0 ;i<Content.GroupWithUser.size();i++){
            if(Content.GroupWithUser.get(i).getGroupName().equals(value)){
                indexOfGroup=i;
            }

        }

        String users = "";
            for (Test s : Content.GroupWithUser.get(indexOfGroup).getList()) {
                users = users + s.getLogin() + ", " ;
            }
        gamers.setText(users);


    }

    public void back(View view){
        Intent myIntent = new Intent(GroupActivity.this, MainActivity.class);

        GroupActivity.this.startActivity(myIntent);
    }

    public void addUserToGroup(View view){
        Intent myIntent = new Intent(GroupActivity.this, AddUserToGroupActivity.class);
        myIntent.putExtra("nameGroup", value);
        GroupActivity.this.startActivity(myIntent);
    }


    public void changeAbout(View view){
        about.getText();
    }

    public void randGame(View view) {
        Random r = new Random();
        int i1 = r.nextInt(Content.GAMES.size() - 1) + 1;

        SetGame setGame = new SetGame(Content.GAMES.get(i1).getContent());
        setGame.execute((Void) null);
        //game_rand.setText(Content.GAMES.get(i1).getContent());
        //setTextGame(Content.GAMES.get(i1).getContent());

    }
   public void setTextGame(String title){
       game_rand.setText(title);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        layout.setVisibility(show ? View.GONE : View.VISIBLE);
        layout.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                layout.setVisibility(show ? View.GONE : View.VISIBLE);
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

    class SetAbout extends AsyncTask<Void, Void, Boolean> {

        private final String title;
        SetAbout(String title) {
            this.title=title;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ServerConnection connection2 = new ServerConnection(ServerConnection.SERVER_URL + "/group/getGame?nameGroup=" +  UsefullValues.group);
            String respone2 = "";

            try {
                respone2=connection2.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }catch (RuntimeException e){
                return true;
            }
            UsefullValues.game=respone2;
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if(success){

                showProgress(false);
                setTextGame(UsefullValues.game);
                Toast.makeText(GroupActivity.this, title, Toast.LENGTH_SHORT).show();

            }


        }
    }
    class SetGame extends AsyncTask<Void, Void, Boolean> {

        private final String title;
        SetGame(String title) {
            this.title=title;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/group/setGame?nameGroup=" + value + "&nameGame=" + title);
            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
           if(responsee.equals("Succes")){
               return true;
           }


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                Toast.makeText(GroupActivity.this, title, Toast.LENGTH_LONG).show();
                setTextGame(title);

            }


        }
    }



    class GetInfo extends AsyncTask<Void, Void, Boolean> {
        private String titleGameFromSerwer;
        private final String title;
        GetInfo(String title) {
            this.title=title;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/user/group/name?name=" + title);
            ServerConnection connection2 = new ServerConnection(ServerConnection.SERVER_URL + " /group/getGame?nameGroup=" + title);

            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                titleGameFromSerwer = connection2.getResponse();
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

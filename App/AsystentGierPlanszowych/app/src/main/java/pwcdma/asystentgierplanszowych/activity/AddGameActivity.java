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
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
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
import pwcdma.asystentgierplanszowych.server.GamesController;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

public class AddGameActivity extends AppCompatActivity {

    private GameTask gameTask = null;
    String title = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgame);
    }

    public void addGame(View view) {
        TextView tv = (TextView) findViewById(R.id.addGameText);
        title = tv.getText().toString();
        gameTask = new GameTask(title);
        gameTask.execute((Void) null);

        this.finish();
    }

    class GameTask extends AsyncTask<Void, Void, Boolean> {

        private final String title;

        GameTask(String title) {
           this.title=title;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
           gamesAdd();
           return false;
        }

        protected void gamesAdd() {
            GroupControllerSerwer g = new GroupControllerSerwer();

                g.addGroup(title);
                groupGet();

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

    }



}

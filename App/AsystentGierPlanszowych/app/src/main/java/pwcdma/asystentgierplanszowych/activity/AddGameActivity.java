package pwcdma.asystentgierplanszowych.activity;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.server.GroupsController;

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
            GroupsController g = new GroupsController();

            try {
                g.addGroup(title);
            } catch (IOException e) {
                e.printStackTrace();
            }
            groupGet();

        }

        protected void groupGet() {
            GroupsController gf = new GroupsController();
            String responsee = null;
            try {
                responsee = gf.getAllGroups();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Type listType = new TypeToken<ArrayList<Group>>(){}.getType();
            List<Group> gamesListFromServer = new Gson().fromJson(responsee, listType);
            for(Group g : gamesListFromServer){
                Content.Item item = new Content.Item(Integer.toString(g.getId()), g.getGroupName(),"");
                Content.addGroup(item);
            }
        }

    }



}

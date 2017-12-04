package pwcdma.asystentgierplanszowych.activity;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

public class AddGroupActivity extends AppCompatActivity {

    private GroupTask groupTask = null;
    String title = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    public void addGame(View view) {
        TextView tv = (TextView) findViewById(R.id.addGameText);
        title = tv.getText().toString();
        groupTask = new GroupTask(title);
        groupTask.execute((Void) null);

        this.finish();
    }

    class GroupTask extends AsyncTask<Void, Void, Boolean> {

        private final String title;

        GroupTask(String title) {
           this.title=title;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

           if(gamesAdd()) {
               groupGet();
              return true;

           }
            Toast.makeText(AddGroupActivity.this, "Nie udało się dodać gry", Toast.LENGTH_LONG).show();
           return false;
        }

        protected Boolean gamesAdd() {
            GroupControllerSerwer g = new GroupControllerSerwer();

                return g.addGroup(title);


        }

        protected void groupGet() {
            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/getGroupsForUser?name=" + UsefullValues.name);
            // ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/getUsersForGroupJSON?name=" + UsefullValues.name);
            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Type listType = new TypeToken<ArrayList<Group>>(){}.getType();
            List<Group> gamesListFromServer = new Gson().fromJson(responsee, listType);
            Content.clearList(Content.GROUPS, Content.GROUP_MAP);


            int i = 0;
            for(Group g : gamesListFromServer){
                Content.Item item = new Content.Item(String.valueOf(++i), g.getGroupName(),"",null);
                Content.addGroup(item);
            }
        }

    }





}

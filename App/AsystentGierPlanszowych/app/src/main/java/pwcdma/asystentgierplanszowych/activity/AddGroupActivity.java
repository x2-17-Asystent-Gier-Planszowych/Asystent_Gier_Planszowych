package pwcdma.asystentgierplanszowych.activity;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;

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
            Content.clearList(Content.GROUPS, Content.GROUP_MAP);
            for(Group g : gamesListFromServer){
                Content.Item item = new Content.Item(Integer.toString(g.getId()), g.getGroupName(),"");
                Content.addGroup(item);
            }
        }

    }





}

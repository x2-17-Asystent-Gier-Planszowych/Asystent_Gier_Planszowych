package pwcdma.asystentgierplanszowych.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.model.GroupWithUser;
import pwcdma.asystentgierplanszowych.model.Test;
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

public class AddUserToGroupActivity extends AppCompatActivity {

    private EditText usernameText;
    private Button addButton;
    private String groupName;
    private AddUserToGroupTask addUserToGroupTask;
    private String userName ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_to_group);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            groupName = extras.getString("nameGroup");
        }
        addButton = (Button) findViewById(R.id.add_button);
        fillDropdown();
    }


    public void addUserToGroup(View view) {
        Spinner dropdown = (Spinner)findViewById(R.id.user_spinner);
        userName = dropdown.getSelectedItem().toString();
        addUserToGroupTask = new AddUserToGroupTask(userName);
        addUserToGroupTask.execute((Void) null);

    }

    public void change(){
        Intent myIntent = new Intent(AddUserToGroupActivity.this, GroupActivity.class);
        myIntent.putExtra("nameGroup", groupName);
        AddUserToGroupActivity.this.startActivity(myIntent);
    }

    protected void fillDropdown(){
        Spinner dropdown = (Spinner)findViewById(R.id.user_spinner);
        String[] items =new String[Content.USER.size()];
        for(int i =0;i< Content.USER.size();i++){
            items[i]=Content.USER.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }


    private class AddUserToGroupTask extends AsyncTask<Void, Void, Boolean> {

        private String username;
        private GroupControllerSerwer controller;

        AddUserToGroupTask(String username) {
            this.username = username;
            controller = new GroupControllerSerwer();
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                 return  groupGet();

            }catch (RuntimeException e){
                return  false;
            }
        }
        protected Boolean groupGet()  {


            boolean b = controller.addUserToGroupByName(username, groupName);
            Content.GroupWithUser.clear();
            if(b) {
                for (Content.Item g : Content.GROUPS) {
                    ServerConnection connection2 = new ServerConnection(ServerConnection.SERVER_URL + "/user/group/name?name=" + g.getContent());
                    String respone2 = "";

                    try {
                        respone2 = connection2.getResponse();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Type listString = new TypeToken<ArrayList<Test>>() {
                    }.getType();
                    List<Test> userList = new Gson().fromJson(respone2, listString);
                    GroupWithUser gwu = new GroupWithUser();
                    gwu.setGroupName(g.getContent());
                    gwu.setList(userList);
                    Content.GroupWithUser.add(gwu);
                }
            }

            return b;

        }
        @Override
        protected void onPostExecute(Boolean success) {
            int msg;
            if (success) {
                msg = R.string.added_user;
                change();
            }
            else
                msg = R.string.add_user_error;

            Toast.makeText(AddUserToGroupActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    }
}
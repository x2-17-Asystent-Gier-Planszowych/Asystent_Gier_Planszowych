package pwcdma.asystentgierplanszowych.activity;

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

import java.io.IOException;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;

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
        usernameText = (EditText) findViewById(R.id.username_text);
        addButton = (Button) findViewById(R.id.add_button);
        fillDropdown();
    }


    public void addUserToGroup(View view) {
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        userName = dropdown.getSelectedItem().toString();
        addUserToGroupTask = new AddUserToGroupTask(userName);
        addUserToGroupTask.execute((Void) null);
        this.finish();
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
            return controller.addUserToGroupByName(username, groupName);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            int msg;
            if (success)
                msg = R.string.added_user;
            else
                msg = R.string.add_user_error;

            Toast.makeText(AddUserToGroupActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    }
}
package pwcdma.asystentgierplanszowych.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.server.GroupsController;

public class AddUserToGroupActivity extends AppCompatActivity {

    private EditText usernameText;
    private Button addButton;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_to_group);

        usernameText = (EditText) findViewById(R.id.username_text);
        addButton = (Button) findViewById(R.id.add_button);
        setButton();
    }

    private void setButton(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                new AddUserToGroupTask(username).execute();
            }
        });
    }

    private class AddUserToGroupTask extends AsyncTask<Void, Void, Boolean> {

        private String username;
        private GroupsController controller;

        AddUserToGroupTask(String username) {
            this.username = username;
            controller = new GroupsController();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return controller.addUserToGroupByName(username, groupName);
            } catch (IOException e){
                return false;
            }
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

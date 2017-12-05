package pwcdma.asystentgierplanszowych.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

/**
 * Created by kriscool on 04.12.2017.
 */

public class EditDate  extends AppCompatActivity {
    private EditText emailText,aboutText;
    private TextView logintext;
    private  WaitFroDate waitFroDate;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_log);
        logintext = (TextView) findViewById(R.id.username_text);
        logintext.setText(UsefullValues.name);
        emailText =(EditText) findViewById(R.id.email_text);
        emailText.setText(UsefullValues.email);
        aboutText =(EditText) findViewById(R.id.about_text);
        aboutText.setText(UsefullValues.about);
    }

    public void onClickSubmit(View view){
        String login = logintext.getText().toString();
        String email = emailText.getText().toString();
        String about = aboutText.getText().toString();
        waitFroDate = new WaitFroDate(login,email,about);
        waitFroDate.execute((Void) null);

    }


    class WaitFroDate extends AsyncTask<Void, Void, Boolean> {
        private final String login;
        private final String email;
        private final String about;
        WaitFroDate(String log,String email,String about){
            this.login=log;
            this.email=email;
            this.about=about;
        }



        @Override
        protected Boolean doInBackground(Void... params) {
            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/change/about?about=" + about + "&login="+ login);
            ServerConnection connection3 = new ServerConnection(ServerConnection.SERVER_URL + "/change/email?email=" + email + "&login="+ login);

            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String responsee2 = null;
            try {
                responsee2 = connection3.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(responsee.equals("Succes") && responsee2.equals("Succes")){
                return  true;
            }else{

            }
            return true;
        }






        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                Intent myIntent = new Intent(EditDate.this, MainActivity.class);
                EditDate.this.startActivity(myIntent);
            }else{

            }
        }

        @Override
        protected void onCancelled() {
        }
    }

}

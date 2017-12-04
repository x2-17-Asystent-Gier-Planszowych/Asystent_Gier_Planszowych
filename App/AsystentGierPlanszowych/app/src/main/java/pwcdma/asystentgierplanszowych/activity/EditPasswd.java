package pwcdma.asystentgierplanszowych.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

/**
 * Created by kriscool on 04.12.2017.
 */

public class EditPasswd extends AppCompatActivity {
    private EditText passwd;
    private TextView logintext;
    private Passwd waitFroDate;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_passwd);
        logintext = (TextView) findViewById(R.id.username_text);
        logintext.setText(UsefullValues.name);
        passwd = (EditText) findViewById(R.id.passwd_text);
    }

    public void onClickSubmit(View view) {
        String login = logintext.getText().toString();
        String pasword = passwd.getText().toString();
        waitFroDate = new Passwd(login, pasword);
        waitFroDate.execute((Void) null);

    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuffer hashStringBuffer = new StringBuffer();
            for (byte b : digest)
                hashStringBuffer.append(String.format("%02X", b));
            return new String(hashStringBuffer);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    class Passwd extends AsyncTask<Void, Void, Boolean> {
        private final String login;
        private final String pass;

        Passwd(String log, String pasword) {
            this.login = log;
            this.pass = pasword;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/change/password?login=" + login + "&haslo=" + hashPassword(pass));

            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (responsee.equals("Succes")) {
                return true;
            } else {

            }
            return true;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Intent myIntent = new Intent(EditPasswd.this, MainActivity.class);
                EditPasswd.this.startActivity(myIntent);
            } else {

            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
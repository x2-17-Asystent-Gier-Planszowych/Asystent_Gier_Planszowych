package pwcdma.asystentgierplanszowych.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.UsefullValues;

/**
 * Created by kriscool on 03.12.2017.
 */

public class GroupActivity extends AppCompatActivity {
    private TextView group_label;
    private TextView game_rand;
    private  String value;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        group_label = (TextView) findViewById(R.id.group_label);
        game_rand = (TextView) findViewById(R.id.Game_rand);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("nameGroup");
        }

        group_label.setText("Grupa \n" + value);
    }

    public void addUserToGroup(View view){
        Intent myIntent = new Intent(GroupActivity.this, AddUserToGroupActivity.class);
        myIntent.putExtra("nameGroup", value);
        GroupActivity.this.startActivity(myIntent);
    }
    public void randGame(View view) {
        Random r = new Random();
        int i1 = r.nextInt(Content.GAMES.size() - 1) + 1;
        game_rand.setText(Content.GAMES.get(i1).getContent());
    }
}

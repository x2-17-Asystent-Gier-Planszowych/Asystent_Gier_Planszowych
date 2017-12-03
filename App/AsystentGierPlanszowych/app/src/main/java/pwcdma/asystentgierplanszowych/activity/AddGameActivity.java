package pwcdma.asystentgierplanszowych.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.model.Categorie;
import pwcdma.asystentgierplanszowych.model.Game;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.server.GamesController;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

/**
 * Created by kriscool on 03.12.2017.
 * kzsysztow nooob
 */

public class AddGameActivity extends AppCompatActivity {
    private GameTask gameTask = null;
    String title = null;
    String tag = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        fillDropdown();
    }

    public void addGame(View view) {
        TextView tv = (TextView) findViewById(R.id.addGameText);
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        tag = dropdown.getSelectedItem().toString();
        Log.d("jakas nazwa", tag);
        title = tv.getText().toString();
        gameTask = new AddGameActivity.GameTask(title,tag);
        gameTask.execute((Void) null);

        this.finish();
    }




    protected void fillDropdown(){
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);

        String[] items = new String[]{"MMO", "test", "test2" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }


    class GameTask extends AsyncTask<Void, Void, Boolean> {

        private final String title;
        private final String tags;
        GameTask(String title,String tags) {
            this.title=title;
            this.tags = tags;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                gamesAdd();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void gamesAdd() throws IOException {
            GamesController gc = new GamesController();
            gc.addGame(title, tags);
            gamesGet();
        }

        protected  void addTag(){
           // ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/games/addCat?name=" + title + "&category=" + tags);
          //  gamesGet();
        }


        protected void gamesGet() {

            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/games");
            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
            List<Game> gamesListFromServer = new Gson().fromJson(responsee, listType);
            Content.GAMES.clear();
            int i = 0;
            for(Game g : gamesListFromServer){
                i++;
                Content.Item item = new Content.Item(String.valueOf(i),g.getName(),"", null);
                Content.addGame(item);
            }
        }


    }

}

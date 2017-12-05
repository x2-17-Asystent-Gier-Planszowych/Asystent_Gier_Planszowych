package pwcdma.asystentgierplanszowych.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import pwcdma.asystentgierplanszowych.model.Game;
import pwcdma.asystentgierplanszowych.model.Group;
import pwcdma.asystentgierplanszowych.model.UsefullValues;
import pwcdma.asystentgierplanszowych.server.GroupControllerSerwer;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

public class TagActivity extends AppCompatActivity {
    private WaitFroDate waitFroDate;
    public class Item {
        boolean checked;
        String ItemString;
        Item( String t, boolean b){
            ItemString = t;
            checked = b;
        }
        public String getItemString(){
            return ItemString;
        }
        public boolean isChecked(){
            return checked;
        }
    }

    static class ViewHolder {
        CheckBox checkBox;
        ImageView icon;
        TextView text;
    }

    public class ItemsListAdapter extends BaseAdapter {

        private Context context;
        private List<Item> list;

        ItemsListAdapter(Context c, List<Item> l) {
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public boolean isChecked(int position) {
            return list.get(position).checked;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.row, null);

                viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
                viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
                viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            viewHolder.checkBox.setChecked(list.get(position).checked);

            final String itemStr = list.get(position).ItemString;
            viewHolder.text.setText(itemStr);

            viewHolder.checkBox.setTag(position);

            /*
            viewHolder.checkBox.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).checked = b;

                    Toast.makeText(getApplicationContext(),
                            itemStr + "onCheckedChanged\nchecked: " + b,
                            Toast.LENGTH_LONG).show();
                }
            });
            */

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;

                }
            });

            viewHolder.checkBox.setChecked(isChecked(position));

            return rowView;
        }
    }

    ImageButton btnLookup;
    List<Item> items;
    ListView listView;
    ItemsListAdapter myItemsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        listView = (ListView)findViewById(R.id.listview);
        btnLookup = (ImageButton)findViewById(R.id.lookup);initItems();
        myItemsListAdapter = new ItemsListAdapter(this, items);
        listView.setAdapter(myItemsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }});

        btnLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "";

                for (int i=0; i<items.size(); i++){
                    if (items.get(i).isChecked()){
                        str +=items.get(i).getItemString();
                    }

                if(str.equals("Bitewna") || str.equals("Ekonomiczna") || str.equals("Karciana") || str.equals("Logiczna") || str.equals("MMO") || str.equals("Online") || str.equals("Strategiczna")){
                    waitFroDate = new WaitFroDate(str,TagActivity.this );
                    waitFroDate.execute((Void) null);
                    Toast.makeText(getApplicationContext(),
                            "Wybierz  tag",
                            Toast.LENGTH_LONG).show();}
                    else{
                    Toast.makeText(getApplicationContext(),
                            "Wybierz tylko jeden tag",
                            Toast.LENGTH_LONG).show();

                 }
                }

                /*
                int cnt = myItemsListAdapter.getCount();
                for (int i=0; i<cnt; i++){
                    if(myItemsListAdapter.isChecked(i)){
                        str += i + "\n";
                    }
                }
                */



            }
        });
    }

    private void initItems(){
        items = new ArrayList<Item>();

        TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);

        for(int i=0; i<arrayText.length(); i++){
            String s = arrayText.getString(i);
            boolean b = false;
            Item item = new Item(s, b);
            items.add(item);
        }

        arrayText.recycle();
    }
    public void clearTag(View view){
        UsefullValues.isTaged = true;
        Intent myIntent = new Intent(TagActivity.this, MainActivity.class);
        TagActivity.this.startActivity(myIntent);
    }
    class WaitFroDate extends AsyncTask<Void, Void, Boolean> {
        private final String tag1;
        private final Activity activity;
        WaitFroDate(String tag1,Activity activity){
            this.tag1=tag1;
            this.activity=activity;
        }



        @Override
        protected Boolean doInBackground(Void... params) {

            gamesGet();
            UsefullValues.isTaged=false;
            return true;
        }


        protected void gamesGet() {

            ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/games/getByTags?tag1=" + tag1);
            String responsee = null;
            try {
                responsee = connection.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Content.GAMES.clear();
            Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
            List<Game> gamesListFromServer = new Gson().fromJson(responsee, listType);
            for(Game g : gamesListFromServer){
                Content.Item item = new Content.Item(g.getId().toString(),g.getName(),"",null);
                Content.addGame(item);
            }
        }





        @Override
        protected void onPostExecute(final Boolean success) {
            // mAuthTask = null;
            // showProgress(false);

                activity.startActivity(new Intent(activity, MainActivity.class));

        }

        @Override
        protected void onCancelled() {
            //  showProgress(false);
        }
    }
}

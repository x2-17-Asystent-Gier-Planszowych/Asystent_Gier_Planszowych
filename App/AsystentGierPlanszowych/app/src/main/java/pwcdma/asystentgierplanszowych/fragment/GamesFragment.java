package pwcdma.asystentgierplanszowych.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.activity.LogInActivity;
import pwcdma.asystentgierplanszowych.activity.MainActivity;
import pwcdma.asystentgierplanszowych.adapter.GameRecyclerViewAdapter;
import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.content.Content;
import pwcdma.asystentgierplanszowych.content.Content.Item;
import pwcdma.asystentgierplanszowych.model.Game;
import pwcdma.asystentgierplanszowych.server.GamesController;
import pwcdma.asystentgierplanszowych.server.ServerConnection;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GamesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private GamesController controller;
    private TableLayout games;
    private GetGamesTask mGameTask = null;
    private List<Game> gamesList = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GamesFragment() {
        if (controller == null)
            controller = new GamesController();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mGameTask = new GetGamesTask();
        mGameTask.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);
        games = view.findViewById(R.id.games);
        setGames();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new GameRecyclerViewAdapter(Content.ITEMS, mListener));
        }


        return view;
    }


    private void setGames() {
        if (games == null) {
            return;
        }
        games.setStretchAllColumns(true);
        games.bringToFront();
        for(int i = 0; i < gamesList.size(); i++){
            TableRow tr =  new TableRow(getActivity());
            TextView c1 = new TextView(getActivity());
            c1.setText(gamesList.get(i).getName());
            tr.addView(c1);
            games.addView(tr);
        }
    }

/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Item item);
    }


    private class GetGamesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... args) {
            try {
                ServerConnection connection = new ServerConnection(ServerConnection.SERVER_URL + "/games");
                String response = connection.getResponse();

                return response;
            } catch (IOException e){
                return getGamesFromFile();
            }
        }

        private String getGamesFromFile() {
            File gamesFile = new File(getContext().getFilesDir(), "games.json");
            if (gamesFile.exists()){
                return readFile(gamesFile);
            } else if (((MainActivity) getActivity()).isOnline()) {
                String data = null;
                try {
                    data = controller.getAllGames();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writeFile(gamesFile, data);
                return data;
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String response) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Game>>(){}.getType();
            List<Game> gamesListFromServer = new Gson().fromJson(response, listType);
            gamesList = gamesListFromServer;
        }

        private void setGameList(List<Game> gamesList) {

        }

        private String readFile(File f){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String data = reader.readLine();
                reader.close();
                return data;
            } catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }
        }

        private void writeFile(File f, String data){
            try {
                FileWriter writer = new FileWriter(f);
                writer.write(data);
                writer.close();
            } catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}

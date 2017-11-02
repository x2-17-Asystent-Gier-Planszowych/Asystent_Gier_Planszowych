import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kamil on 2017-11-02.
 */
public class GamesControllerSerwer {

    //private final String ipSerwer="https://gentle-journey-42470.herokuapp.com";
    private final String ipSerwer="http://localhost:8080";

    public String getAllGames() { return connectToSerwer(ipSerwer+"/games");}
    public Boolean addGame(String name){
        if(connectToSerwer(ipSerwer+"/games/addGame?name="+name).equals("Success"))
            return true;
        else
            return false;
    }
    public Boolean addTagsToGame(String game, String category){
        if(connectToSerwer(ipSerwer+"/games/addCat?name="+game+"&category="+category).equals("Succes"))
            return true;
        else
            return false;
    }
    public String getTagsFromGame(String game){
        return connectToSerwer(ipSerwer+"/games/getTags?name="+game);
    }
    public String getGameByName(String game){
        return connectToSerwer(ipSerwer+"/games/"+game);
    }
    // not safe
    public Boolean deleteGame(String game){
        if(connectToSerwer(ipSerwer+ "/games/"+game+"/inactive").equals("Success"))
            return true;
        else
            return false;
    }



    public String connectToSerwer(String urlToSend){
        String output = null;
        try {

            URL url = new URL(urlToSend);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                System.out.println(conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                return output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {



        } catch (IOException e) {



        }
        return output;

    }
}

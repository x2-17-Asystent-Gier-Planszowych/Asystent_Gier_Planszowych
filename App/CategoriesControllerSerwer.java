import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kamil on 2017-11-02.
 */
public class CategoriesControllerSerwer {
    private final String ipSerwer="https://gentle-journey-42470.herokuapp.com";
    

    public String getAllCategories() { return connectToSerwer(ipSerwer+"/categories");}
    public String getCategoryByName(String name) {return connectToSerwer(ipSerwer+"/categories/"+name);}
    public Boolean addCategory(String name) {
       if(connectToSerwer(ipSerwer+"/categories/add?name="+name).equals("Success"))
            return true;
       else
           return false;
    }
    // not safe to use right now
    public Boolean deleteCategory(String name){
     if(connectToSerwer(ipSerwer+"/categories/"+name+"/inactive").equals("Success"))
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

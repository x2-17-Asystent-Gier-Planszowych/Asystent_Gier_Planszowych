package pwcdma.asystentgierplanszowych.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnection {

    public static final String SERVER_URL = "https://gentle-journey-42470.herokuapp.com";
    private HttpURLConnection connection;

    public ServerConnection(String urlToSend){
        try {
            URL url = new URL(urlToSend);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getResponse() throws IOException {
        String response = "";
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        response = reader.readLine();

        connection.disconnect();

        return response;
    }
}

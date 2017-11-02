package pwcdma.asystentgierplanszowych.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import javax.xml.bind.DatatypeConverter;

public class ServerConnection {

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

    public String getResponse(){
        String response = "";
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine();

            connection.disconnect();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return response;
    }

    /*public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return myHash;
    }*/
}

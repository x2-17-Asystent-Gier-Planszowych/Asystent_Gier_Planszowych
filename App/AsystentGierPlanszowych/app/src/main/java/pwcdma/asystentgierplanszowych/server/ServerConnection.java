package pwcdma.asystentgierplanszowych.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static boolean isUsernameValid(String username) {
        return username.matches("[A-Za-z0-9_]{4,}");
    }

    public static boolean isEmailValid(String email) {
        return email.matches("[A-Za-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}");
    }

    public static boolean isPasswordValid(String password) {
        return password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[0-9].*")
                && password.length() >= 8;
    }
}

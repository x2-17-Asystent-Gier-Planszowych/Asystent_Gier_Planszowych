package pwcdma.asystentgierplanszowych.model;

import java.io.*;

import org.json.*;

public class User {

    public static final String FILE_NAME = "user_data.json";

    private String username;
    private String email;
    private String password;
    private String about;

    public User(String username, String email, String password, String about) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.about = about;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.about = "";
    }

    public User(String userData){
        try {
            JSONObject json = new JSONArray(userData).getJSONObject(0);
            username = json.getString("username");
            email = json.getString("email");
            if (json.has("about"))
                about = json.getString("about");
            else
                about = "";
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAbout() {
        return about;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void saveUserData(File userDataFile){
        try {
            JSONObject userDataJson = new JSONObject();
            userDataJson.put("username", username);
            userDataJson.put("email", email);
            userDataJson.put("password", password);
            userDataJson.put("about", about);
            String userData = userDataJson.toString();
            FileWriter writer = new FileWriter(userDataFile);
            writer.write(userData);
            writer.close();
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static User getUserData(File f){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String userData = reader.readLine();
            reader.close();

            JSONObject userDataJson = new JSONObject(userData);
            String username = userDataJson.getString("username");
            String email = userDataJson.getString("email");
            String password = userDataJson.getString("password");
            String about = userDataJson.getString("about");

            return new User(username, email, password, about);
        } catch (IOException|JSONException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

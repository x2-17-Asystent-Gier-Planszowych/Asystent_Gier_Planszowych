package pwcdma.asystentgierplanszowych.model;

import java.io.*;

import org.json.*;

public class User {

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

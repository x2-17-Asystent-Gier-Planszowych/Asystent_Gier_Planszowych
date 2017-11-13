package pwcdma.asystentgierplanszowych.server;

import java.io.IOException;

public class UserController {

    public String getAllUsers() throws IOException {
        return new ServerConnection(ServerConnection.SERVER_URL + "/get/users")
                .getResponse();
    }

    public Boolean signIn(String login, String password) throws IOException {
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/signin?login=" + login + "&haslo=" + password)
                .getResponse();
        return response.equals("Succes");
    }

    public Boolean register(String name, String email, String password) throws IOException {
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/registration?" +
                "name=" + name + "&email=" + email + "&password=" + password).getResponse();
        return response.equals("Succes");
    }

    public Boolean changePassword(String login, String password) throws IOException {
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/change/password?login=" + login + "&haslo=" + password)
                .getResponse();
        return response.equals("Succes");
    }

    public Boolean changeEmail(String login, String email) throws IOException {
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/change/email?email=" + email + "&login=" + login)
                .getResponse();
        return response.equals("Succes");
    }

    public Boolean changeAbout(String login, String about) throws IOException {
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/change/about?about=" + about + "&login=" + login)
                .getResponse();
        return response.equals("Succes");
    }

    public Boolean deactivate(String login) throws IOException {
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/deactivate?login=" + login)
                .getResponse();
        return response.equals("Succes");
    }
}

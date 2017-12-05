package pwcdma.asystentgierplanszowych.server;

import java.io.IOException;

public class GamesController {

    public String getAllGames() throws IOException {
        return new ServerConnection(ServerConnection.SERVER_URL + "/games")
                .getResponse();
    }

    public boolean addGame(String name, String tags) throws IOException {
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/games/addGame2?name=" + name + "&tag=" + tags)
                .getResponse();
        return response.equals("Success");
    }

    public String getTagsFromGame(String game) throws IOException {
        return new ServerConnection(ServerConnection.SERVER_URL + "/games/getTags?name=" + game)
                .getResponse();
    }

    public String getGameByName(String game) throws IOException {
        return new ServerConnection(ServerConnection.SERVER_URL + "/games/" + game)
                .getResponse();
    }

    // not safe
    public boolean deleteGame(String game) throws IOException {
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/games/" + game + "/inactive")
                .getResponse();
        return response.equals("Success");
    }
}

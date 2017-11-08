package pwcdma.asystentgierplanszowych.server;

public class GamesController {

    public String getAllGames(){
        return new ServerConnection(ServerConnection.SERVER_URL + "/games")
                .getResponse();
    }

    public boolean addGame(String name){
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/games/addGame?name=" + name)
                .getResponse();
        return response.equals("Success");
    }

    public String getTagsFromGame(String game){
        return new ServerConnection(ServerConnection.SERVER_URL + "/games/getTags?name=" + game)
                .getResponse();
    }

    public String getGameByName(String game){
        return new ServerConnection(ServerConnection.SERVER_URL + "/games/" + game)
                .getResponse();
    }

    // not safe
    public boolean deleteGame(String game){
        String response = new ServerConnection(ServerConnection.SERVER_URL + "/games/" + game + "/inactive")
                .getResponse();
        return response.equals("Success");
    }
}

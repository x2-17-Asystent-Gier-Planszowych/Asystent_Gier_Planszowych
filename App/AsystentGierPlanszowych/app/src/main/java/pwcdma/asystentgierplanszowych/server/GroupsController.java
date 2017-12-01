package pwcdma.asystentgierplanszowych.server;

import java.io.IOException;

public class GroupsController {

	public String getAllGroups() throws IOException {
		return new ServerConnection(ServerConnection.SERVER_URL + "/group")
				.getResponse();
	}
	
	public Boolean addUserToGroupByName(String userName, String groupName) throws IOException {
		String response = new ServerConnection(ServerConnection.SERVER_URL + 
				"/group/add/user/name?nameGroup=" + groupName + "&nameUser=" + userName)
				.getResponse();
		return response.equals("Succes");
	}
	
	public Boolean addUserToGroupById(int userId, int groupId) throws IOException {
		String response = new ServerConnection(ServerConnection.SERVER_URL + 
				"/group/add/user?idGroup=" + groupId + "&idUser=" + userId)
				.getResponse();
		return response.equals("Succes");
	}
	
	public String getUserInGroup(String name) throws IOException {
		return new ServerConnection(ServerConnection.SERVER_URL + 
				"/user/group/name?name="+ name)
				.getResponse();
	}
	
	public Boolean deleteGroup(String name) throws IOException {
		String response = new ServerConnection(ServerConnection.SERVER_URL + 
				"/group/delete?name=" + name)
				.getResponse();
		return response.equals("Succes");
	}
	
	public Boolean deleteUserFromGroup(String nameGroup, String nameUser) throws IOException {
		String response = new ServerConnection(ServerConnection.SERVER_URL + 
				"/group/delete/user?nameUser=" + nameUser + "&nameGroup=" + nameGroup)
				.getResponse();
		return response.equals("Succes");
	}
	
	public Boolean addGroup(String nameGroup) throws IOException {
		String response = new ServerConnection(ServerConnection.SERVER_URL + 
				"/group/add?name=" + nameGroup + "&owner=" + "testtest")
				.getResponse();
		return response.equals("Succes");
	}
}

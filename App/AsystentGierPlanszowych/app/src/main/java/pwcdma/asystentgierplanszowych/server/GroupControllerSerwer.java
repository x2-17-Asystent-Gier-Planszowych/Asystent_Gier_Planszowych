
package pwcdma.asystentgierplanszowych.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pwcdma.asystentgierplanszowych.model.UsefullValues;

public class GroupControllerSerwer {
	private final String ipSerwer="https://gentle-journey-42470.herokuapp.com";
	
	public String getAllGroups(){
		return connectToSerwer(ipSerwer+"/group");
	}
	
	public Boolean addUserToGroupByName(String userName,String groupName){
		String response = connectToSerwer(ipSerwer+"/group/add/user/name?nameGroup="+ groupName + "&nameUser="+userName);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean addUserToGroupById(int userId,int groupId){
		String response = connectToSerwer(ipSerwer+"/group/add/user?idGroup="+ groupId + "&idUser="+userId);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public String getUserInGroup(String name){
		return connectToSerwer(ipSerwer+"/user/group/name?name="+ name);
	}
	
	public Boolean deleteGroup(String name){
		String response = connectToSerwer(ipSerwer+"/group/delete?name="+ name);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean deleteUserFromGroup(String nameGroup,String nameUser){
		String response = connectToSerwer(ipSerwer+"/group/delete/user?nameUser="+ nameUser + "&nameGroup=" + nameGroup);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean addGroup(String nameGroup){
		String response = connectToSerwer(ipSerwer+"/group/add?name="+nameGroup+"&owner="+ UsefullValues.name);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class UserControllerSerwer {
	
	private final String ipSerwer="https://gentle-journey-42470.herokuapp.com";
	
	public String getAllUser(){
		return connectToSerwer(ipSerwer+"/get/users");
	}
	
	public Boolean signIn(String login,String password) throws NoSuchAlgorithmException{
		String response = connectToSerwer(ipSerwer+"/signin?login="+ login + "&haslo="+password);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean registration(String login,String password,String email) throws NoSuchAlgorithmException{
		String response = connectToSerwer(ipSerwer+"/registration?name="+ login +"&email="+email+"&password="+ password);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean changePassword(String login,String password){
		String response = connectToSerwer(ipSerwer+"/change/password?login="+ login + "&haslo="+password);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean changeAbout(String about,String login){
		String response = connectToSerwer(ipSerwer+"/change/about?about="+ about + "&login="+login);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean deactivate(String login){
		String response = connectToSerwer(ipSerwer+"/deactivate?login="+ login);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean changeEmail(String login,String email){
		String response = connectToSerwer(ipSerwer+"/change/email?email="+ email + "&login="+login);
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

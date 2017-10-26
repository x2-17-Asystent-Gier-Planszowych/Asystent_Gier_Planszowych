import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class ConnectToSerwer {
	
	
	
	public Boolean signIn(String login,String password) throws NoSuchAlgorithmException{
		String response = connectToSerwer("https://safe-mesa-80296.herokuapp.com/signin?login="+ login + "&haslo="+password);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean registration(String login,String password,String email) throws NoSuchAlgorithmException{
		String response = connectToSerwer("https://safe-mesa-80296.herokuapp.com/registration?name="+ login + "&email=" + email +"&password="+password);
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class ConnectToSerwer {
	
	
	
	public Boolean signIn(String login,String password) throws NoSuchAlgorithmException{
		String response = connectToSerwer("https://safe-mesa-80296.herokuapp.com/signin?login="+ login + "&haslo="+password);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean registration(String login,String password,String email) throws NoSuchAlgorithmException{
		String response = connectToSerwer("https://safe-mesa-80296.herokuapp.com/registration?name="+ login + "&email=" + email +"&password="+password);
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class ConnectToSerwer {
	
	
	
	public Boolean signIn(String login,String password) throws NoSuchAlgorithmException{
		String response = connectToSerwer("https://safe-mesa-80296.herokuapp.com/signin?login="+ login + "&haslo="+password);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean registration(String login,String password,String email) throws NoSuchAlgorithmException{
		String response = connectToSerwer("https://safe-mesa-80296.herokuapp.com/registration?name="+ login + "&email=" + email +"&password="+password);
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class ConnectToSerwer {
	
	
	public String hashPassword(String password) 
			  throws NoSuchAlgorithmException {
			    MessageDigest md = MessageDigest.getInstance("MD5");
			    md.update(password.getBytes());
			    byte[] digest = md.digest();
			    String myHash = DatatypeConverter
			      .printHexBinary(digest).toUpperCase();
			         return myHash;
	}
	
	public Boolean loginUserOnSerwer(String login,String pass) throws NoSuchAlgorithmException{
		String hashPassword = hashPassword(pass);
		String response = connectToSerwer("http://localhost:8080/login?login="+ login + "&haslo="+hashPassword);
		if(response.equals("Succes")){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean registrUserOnSerwer(String login,String pass,String email) throws NoSuchAlgorithmException{
		String hashPassword = hashPassword(pass);
		String response = connectToSerwer("http://localhost:8080/rejestacja?name="+ login + "&email=" + email +"&password="+hashPassword);
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
					System.out.println(output);
					return output;
				}

				conn.disconnect();
			
			  } catch (MalformedURLException e) {

				

			  } catch (IOException e) {

			

			  }
		return output;
		  
	}
}

package Control.beans;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.*;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import Control.beans.ClientDatabaseServicesBean;
import Model.AuthenticatedUser;



public class GoogleAuthenticatorServiceBean{
	HttpTransport transport = new NetHttpTransport();
	private GsonFactory  jsonFactory= new GsonFactory(); 
	private GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).build();
			
	public AuthenticatedUser verify(String userToken){
		//GeneralSecurityException
		//IOException
		GoogleIdToken idToken;
		try {
			idToken = verifier.verify(userToken);
		} catch (GeneralSecurityException e) {
//add error info here
			idToken = null;
		} catch (IOException e) {
//add error info here
			idToken = null; 
		}
		
		AuthenticatedUser user;  
		ClientDatabaseServicesBean cService = new ClientDatabaseServicesBean(); 
		if (idToken != null) {
		  Payload payload = idToken.getPayload();

		  // Print user identifier
		  String userId = payload.getSubject();
		  System.out.println("User ID: " + userId);

		  // Get profile information from payload
		  String email = payload.getEmail();
		  String familyName = (String) payload.get("family_name");
		  String givenName = (String) payload.get("given_name");
		  user = cService.getAuthenticatedUser(email, givenName, familyName);
		  user.setVerified(Boolean.valueOf(payload.getEmailVerified()));
		  } else {
			 user = null;  
		  }
		cService.close();
		  
		 return user; 
		}
		
}

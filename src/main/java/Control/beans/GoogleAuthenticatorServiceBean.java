package Control.beans;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Model.AuthenticatedUser;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;

public class GoogleAuthenticatorServiceBean{
	NetHttpTransport transport = new NetHttpTransport();
	private JsonFactory jsonFactory= new GsonFactory(); 
	private GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(transport, jsonFactory, null);
			
	public AuthenticatedUser verify(String userToken){
		GoogleIdToken idToken = verifier.verify(userToken);
		if (idToken != null) {
		  Payload payload = idToken.getPayload();

		  // Print user identifier
		  String userId = payload.getSubject();
		  System.out.println("User ID: " + userId);

		  // Get profile information from payload
		  String email = payload.getEmail();
		  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		  String name = (String) payload.get("name");
		  String pictureUrl = (String) payload.get("picture");
		  String locale = (String) payload.get("locale");
		  String familyName = (String) payload.get("family_name");
		  String givenName = (String) payload.get("given_name");
		AuthenticatedUser user = new AuthenticatedUser(); 
		
		return user; 
	}
		
}

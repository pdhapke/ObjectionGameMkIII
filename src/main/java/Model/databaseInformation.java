package Model;

import java.util.HashMap;
import java.util.Map;

public class databaseInformation {
	private databaseInformationLocal local = new databaseInformationLocal(); 
	private String password = local.getPassword(); //for sql
	private String username = local.getUsername(); //for sql
	private String googleClientID = local.getGoogleClientID(); //your id here; 
	private String googleClientSecret = local.getGoogleClientSecret(); //your secret here;
	private String adminuser = local.getAdmin();  //your adminname here
	private String adminpass = local.getAdminPassword();  //your admin password here
	private String location = "jdbc:mysql://localhost:3306/objection_database"; //your sql location here
	
	public  Map<String, String> adminProperties(){
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.user", adminuser);
		properties.put("javax.persistence.jdbc.password", adminpass);
		return properties;
	}
	
	public  Map<String, String> userProperties(){
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.user", username);
		properties.put("javax.persistence.jdbc.password", password);
		System.out.println("databaseinfo");
		System.out.println(username);
		System.out.println(password);

		return properties;
	}
	
	public String getPassword(){
		return password; 
	}
	public String getUsername(){
		return username; 
	}
	public String getGoogleClientID(){
		return googleClientID; 
	}
	public String getGoogleClientSecret(){
		return googleClientSecret; 
	}
	public String getAdmin(){
		return adminuser; 
	}
	public String getAdminPassword(){
		return adminpass; 
	}
	public String location(){
		return location; 
	}
}

package Model;

public class databaseInformation {
	private static String password; 
	private static String username; 
	private static String googleclientID = "your id here"; 
	private static String googleclientSecret = "your secret here";
	
	
	public static String getGoogleclientID() {
		return googleclientID;
	}
	public static String getGoogleclientSecret() {
		return googleclientSecret;
	}

	
	
	
	public static String password(){
		return password; 
		}
	public static String user(){
		return username; 
		}
	
	
	
	
}

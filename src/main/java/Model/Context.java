package Model;

public class Context {
	private int caseID; 
	private String context;
	
	public Context(){};
	public Context(int ID, String con){
		this.caseID = ID;
		this.context = con;
	}
	public String toString(){
		String s = ""; 
		s = s.concat("Case #" + caseID + ": " + this.context).concat("\n");
		return s;
	}
	
	public int getCaseID() {
		return caseID;
	}
	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
	
}

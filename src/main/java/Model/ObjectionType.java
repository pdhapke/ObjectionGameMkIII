package Model;

public class ObjectionType {
	private String objectionType; 
	private String objectionInformation;  //this is just a default objection rules explanation for reference with federal rule #
	private int objectionTypeID;
	
	public String toString(){
		String s = ""; 
		s = s.concat(this.objectionType).concat("\n");;
		s = s.concat(this.objectionTypeID + " - " + this.objectionInformation);
		return s;
		}
	
	public ObjectionType(){}; 
	public ObjectionType(String type, String info, int typeID){
		this.objectionInformation = info; 
		this.objectionType = type; 
		this.objectionTypeID = typeID; 
	}
	
	public int getObjectionTypeID() {
		return objectionTypeID;
	}
	public void setObjectionTypeID(int objectionTypeID) {
		this.objectionTypeID = objectionTypeID;
	}
	public String getObjectionType() {
		return objectionType;
	}
	public void setObjectionType(String objectionType) {
		this.objectionType = objectionType;
	}
	public String getObjectionInformation() {
		return objectionInformation;
	}
	public void setObjectionInformation(String objectionInformation) {
		this.objectionInformation = objectionInformation;
	}
	
}

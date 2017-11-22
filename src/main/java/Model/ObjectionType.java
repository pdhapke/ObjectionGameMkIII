package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name = "objection")
public class ObjectionType {
	
	@Id
	@GeneratedValue
	@Column(name="objection_key")
	private int objectionTypeID;
	@Column(name = "objection_rule_number")
	private int objectionRuleNumber; 
	@Column(name = "objection_type")
	private String objectionType; 
	@Column(name = "explanation")
	private String objectionInformation;  //this is just a default objection rules explanation for reference with federal rule #
	
	
	public String toString(){
		String s = ""; 
		s = s.concat(this.objectionType).concat("\n");;
		s = s.concat(this.objectionRuleNumber + " - " + this.objectionInformation);
		return s;
		}

	public ObjectionType(){}; 
	public ObjectionType(String type, String info, int rule){
		this.objectionInformation = info; 
		this.objectionType = type; 
		this.objectionRuleNumber = rule; 
	}
	public ObjectionType(String type, String info, int rule, int typeID){
		this.objectionInformation = info; 
		this.objectionType = type; 
		this.objectionRuleNumber = rule; 
		this.objectionTypeID = typeID; 
	}
	public int getObjectionRuleNumber() {
		return objectionRuleNumber;
	}

	public void setObjectionRuleNumber(int objectionRuleNumber) {
		this.objectionRuleNumber = objectionRuleNumber;
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
	public void update(ObjectionType type){
		this.objectionInformation = type.getObjectionInformation(); 
		this.objectionType = type.getObjectionType(); 
		this.objectionTypeID = type.getObjectionTypeID(); 
	}
	public void update(String type, String info, int typeID){
		this.objectionInformation = info; 
		this.objectionType = type; 
		this.objectionTypeID = typeID; 
	}
	public String getJsonString(){
		System.out.println("In jsonifier");
		Gson json = new Gson();
		ObjectionType type = this; 
		String output = json.toJson(type); 
		System.out.println(output);
		return output;
	}
}

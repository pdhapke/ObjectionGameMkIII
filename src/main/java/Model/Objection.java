package Model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;


@Entity
@Table (name = "correct_objection")
public class Objection {
@Id
@GeneratedValue
@Column(name="correct_objection_id")
private int objectionID; 

@Column (name = "question_id")
private int fk_questionID; 

@Column(name = "objection_key")
private int fk_objectionTypeID;
@Column(name="correct_reason")
private String explanation; //more detailed reason for why it is correct
@Column(name="correct_time")
private String timing; //when the objection should be made (is)(calls for)(witness has begun)

@Transient 
private ObjectionType description;

public String toString(){
	String s = ""; 
	s = s.concat("This objection is: ").concat(this.description.toString()).concat("\n");;
	s = s.concat("Because: " + this.explanation + " It is objectionable due to the " + this.timing + ". ");
	return s;
}



public Objection(){}; 

public Objection(ObjectionType objType, String exp, String time, int objID, int fkQ, int fkOT){
	this.description = objType;
	this.explanation = exp; 
	this.timing = time; 
	this.objectionID = objID; 
	this.fk_questionID = fkQ; 
	this.fk_objectionTypeID = fkOT;
}

public Objection(ObjectionType objType, String exp, String time, int objID, int fkQ){
	this.description = objType;
	this.explanation = exp; 
	this.timing = time; 
	this.objectionID = objID; 
	this.fk_questionID = fkQ; 
	this.fk_objectionTypeID = objType.getObjectionTypeID();
}

public int getObjectionID() {
	return objectionID;
}
public ObjectionType getDescription() {
	return description;
}

public void setDescription(ObjectionType description) {
	this.description = description;
}

public int getFk_objectionTypeID() {
	return fk_objectionTypeID;
}

public void setFk_objectionTypeID(int fk_objectionTypeID) {
	this.fk_objectionTypeID = fk_objectionTypeID;
}
public void setObjectionID(int objectionID) {
	this.objectionID = objectionID;
}

public int getFk_questionID() {
	return fk_questionID;
}

public void setFk_questionID(int fk_questionID) {
	this.fk_questionID = fk_questionID;
}
public String getObjectionType() {
	return this.description.getObjectionType();
}
public void setObjectionType(String objectionType) {
	this.description.setObjectionType(objectionType);
}
public String getObjectionInformation() {
	return this.description.getObjectionInformation();
}
public void setObjectionInformation(String objectionInformation) {
	this.description.setObjectionInformation(objectionInformation);
}
public String getExplanation() {
	return explanation;
}
public void setExplanation(String explanation) {
	this.explanation = explanation;
}
public String getTiming() {
	return timing;
}
public void setTiming(String timing) {
	this.timing = timing;
}
public void update(Objection ob){
	this.explanation = ob.getExplanation(); 
	this.timing = ob.getTiming(); 
	this.objectionID = ob.getObjectionID(); 
	this.fk_questionID = ob.getFk_questionID(); 
	this.fk_objectionTypeID = ob.getFk_objectionTypeID();
}
public void update(String exp, String time, int objID, int fkQ, int fkOT){
	this.explanation = exp; 
	this.timing = time; 
	this.objectionID = objID; 
	this.fk_questionID = fkQ; 
	this.fk_objectionTypeID = fkOT;
}
public String getJsonString(){
	System.out.println("In jsonifier");
	Gson json = new Gson();
	Objection ob = this; 
	String output = json.toJson(ob); 
	System.out.println(output);
	return output;
}
}

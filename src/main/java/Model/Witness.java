package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name = "witnesses")
public class Witness {
@Id
@GeneratedValue(strategy=GenerationType.SEQUENCE)
@Column(name = "witness_id")
private int witnessID; 
@Column(name = "case_id")
private int fk_caseID;
@Column(name="first_name")
private String firstname; 
@Column(name="last_name")
private String lastname; 
@Column(name="affidavit")
private String affidavit; 
@Column(name="side")
private String side; 




public Witness(){};

public Witness(String fname, String lname, String aff, String side,  int witID, int caseID){
	this.firstname = fname; 
	this.lastname = lname; 
	this.affidavit = aff; 
	this.side = side; 
	this.fk_caseID = caseID; 
	this.witnessID = witID; 
}
public Witness(String fname, String lname, String aff, String side){
	this.firstname = fname; 
	this.lastname = lname; 
	this.affidavit = aff; 
	this.side = side; 
}
public String toString(){
	String s = ""; 
	s = s.concat("Witness - " + this.firstname + " " + this.lastname + ", Side:  " + this.side).concat("\n");
	s = s.concat("--Statement--").concat("\n");
	s = s.concat(this.affidavit).concat("\n");
	s = s.concat("Witness ID: " + this.witnessID);
	
	return s;
}

public int getFk_caseID() {
	return fk_caseID;
}
public int getWitnessID() {
	return witnessID;
}

public void setWitnessID(int witnessID) {
	this.witnessID = witnessID;
}
public void setFk_caseID(int fk_caseID) {
	this.fk_caseID = fk_caseID;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getLastname() {
	return lastname;
}

public void setLastname(String lastname) {
	this.lastname = lastname;
}

public String getAffidavit() {
	return affidavit;
}

public void setAffidavit(String affidavit) {
	this.affidavit = affidavit;
}

public String getSide() {
	return side;
}

public void setSide(String side) {
	this.side = side;
}

public String name(){
	return this.firstname + " " + this.lastname;
}
public String getName(){
	return this.firstname + " " + this.lastname;
}
public void update(String fname, String lname, String aff, String side,  int witID, int caseID){
	this.firstname = fname; 
	this.lastname = lname; 
	this.affidavit = aff; 
	this.side = side; 
	this.fk_caseID = caseID; 
	this.witnessID = witID; 
}
public void update(Witness w){
	this.firstname = w.getFirstname(); 
	this.lastname = w.getLastname(); 
	this.affidavit = w.getAffidavit(); 
	this.side = w.getSide(); 
	this.fk_caseID = w.getFk_caseID(); 
	this.witnessID = w.getWitnessID(); 
}
public String getJsonString(){
	System.out.println("In jsonifier");
	Gson json = new Gson();
	Witness w = this; 
	String output = json.toJson(w); 
	System.out.println(output);
	return output;
}
}

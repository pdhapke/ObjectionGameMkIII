package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table (name = "context")
public class Context {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="case_id")
	private int caseID; 
	@Column(name="context")
	private String context;

	public Context(){	
	};
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
	public void update(Context c){
		this.caseID = c.getCaseID();
		this.context = c.getContext();
	}
	public void update(int ID, String con){
		this.caseID = ID;
		this.context = con;
	}
	public String getJsonString(){
		Gson json = new Gson();
		Context c = this; 
		String output = json.toJson(c); 
		return output;
	}
}

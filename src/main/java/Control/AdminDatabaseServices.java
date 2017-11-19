package Control;
import Model.Question;
import Model.Context;
import Model.Objection;
import Model.ObjectionType;
import Model.Transcript;
import Model.Witness;
import com.google.gson.Gson;
import java.util.List; 
import Model.databaseInformation;

public interface AdminDatabaseServices extends ClientDatabaseServices {
	//Create
	public boolean addQuestions(List<Question> list);
	public boolean addQuestion(Question q);
	public boolean addContext(Context c); 
	public boolean addWitness(Witness w); 
	public boolean addTranscript(Transcript t); 
	public boolean addObjection(Objection obj); 
	public boolean addObjectionType(ObjectionType type); 
	
	
	//Read
	//client services extended for question reading
	
	//Update total
	public boolean updateQuestion(Question q); 
	public boolean updateQuestion(Gson gson); 
	
	//Update individual
	
	public boolean updateContext(Context c); 
	public boolean updateWitness(Witness w); 
	public boolean updateTranscript(Transcript t); 
	public boolean updateObjection(Objection obj); 
	public boolean updateObjectionType(ObjectionType type); 
	
	public boolean updateContext(int ID, String con); 
	public boolean updateWitness(String fname, String lname, String aff, String side,  int witID, int caseID); 
	public boolean updateTranscript(String side, String current, String answer,  int qID, int preID, int fk); 
	public boolean updateObjection(String exp, String time, int objID, int fkQ, int fkOT); 
	public boolean updateObjectionType(String type, String info, int typeID); 
	
	
	
	
	//Delete
	public boolean deleteQuestion(Question q); 
	public boolean deleteQuestion(int questionID); 
	 
	//delete individual
	public boolean deleteContext(Context c); 
	public boolean deleteWitness(Witness w); 
	public boolean deleteTranscript(Transcript t); 
	public boolean deleteObjection(Objection obj); 
	public boolean deleteObjectionType(ObjectionType type); 
	
	public boolean deleteContext(int id); 
	public boolean deleteWitness(int id); 
	public boolean deleteTranscript(int id); 
	public boolean deleteObjection(int id); 
	public boolean deleteObjectionType(int id); 
	}

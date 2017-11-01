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
	
	//Update
	public boolean updateQuestion(Question q); 
	public boolean updateQuestion(Gson gson); 
	
	//Delete
	public boolean deleteQuestion(Question q); 
	public boolean deleteQuestion(int questionID); 
	
	}

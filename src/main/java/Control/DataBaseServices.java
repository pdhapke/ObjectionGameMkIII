package Control;
import Model.Question;
import Model.Objection;
import Model.Transcript;
import Model.Witness;
import com.google.gson.Gson;
import java.util.List; 
import Model.databaseInformation;

public interface DataBaseServices {
	//Create
	public boolean addQuestions(List<Question> list);
	public boolean addQuestion(Question q);
	
	//Read
	public List<Question> getQuestions(String type, int number); 
	public Question getQuestion(int qID); 
	
	//Update
	public boolean updateQuestion(Question q); 
	public boolean updateQuestion(String gson); 
	
	//Delete
	public boolean deleteQuestion(Question q); 
	public boolean deleteQuestion(int questionID); 
	
	}

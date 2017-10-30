package Control;
import Model.Question;
import Model.Objection;
import Model.Transcript;
import Model.Witness;
import Model.databaseInformation;
import java.util.List; 

public interface GetQuestionsFromDataBase {
	//read
	public List<Question> getQuestions(String type, int numberOfQuestions); 
	public Question getQuestion(String type);
	}

package Model.beans;
import Model.Question;
import Model.Objection;
import Model.Transcript;
import Model.Witness;

import java.util.List; 


public interface QuestionList {
	public int size(); 
	public void delete (int questionID);
	public void add(Question question); 
	public void randomize(); 	
	public List<Question> getQuestionList(); 
	public Question get(int i); 
}
	

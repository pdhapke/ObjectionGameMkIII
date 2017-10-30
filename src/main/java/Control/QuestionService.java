package Control;

import java.util.List;

import Model.Question;
import Model.Objection;
import Model.Transcript;
import Model.Witness;

public interface QuestionService {
	public void updateScore(boolean correct);
	public void getNewList(String type, int size);
	public int score();
	public List<Integer> completedQuestions(); 
	public Question getNext(); 
	public Question getCurrent(); 
	public boolean hasNext(); 
	public boolean userObjectsTo(String objectionType, String objectionTime);
	public Objection getCorrectObjection(); 
	public Objection getCorrectObjection(String type); 
	public String toString();
	}

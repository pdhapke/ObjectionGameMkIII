package Control.beans;

import java.util.ArrayList;
import java.util.List;
import Control.GetQuestionsFromDataBase;
import Model.Question;
import Model.databaseInformation;

public class GetQuestionsFromDatabaseBean implements GetQuestionsFromDataBase {

	
	public List<Question> getQuestions(String type, int numberOfQuestions) {
		List<Question> list = new ArrayList<Question>(); 
		Question tempQuestion; 
		boolean isUniqueFlag = true; 
		
		
		
		
		
		while (list.size() < numberOfQuestions){
			tempQuestion = new Question();
			for(Question q : list){
				if (q.equals(tempQuestion)){
					isUniqueFlag = false;
				}
			}
			if(isUniqueFlag){
				list.add(tempQuestion);
			}
			isUniqueFlag = true; 
		}
		return list;
	}


	public Question getQuestion(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}

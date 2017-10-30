package Control.beans;
import java.util.List;

import com.google.gson.Gson;
import Model.Question;
import Control.DataBaseServices;

public class DataBaseServicesBean implements DataBaseServices{

	public boolean addQuestions(List<Question> list) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addQuestion(Question q) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Question> getQuestions(String type, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	public Question getQuestion(int qID) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateQuestion(Question q) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateQuestion(String gson) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteQuestion(Question q) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteQuestion(int questionID) {
		// TODO Auto-generated method stub
		return false;
	}

	
}

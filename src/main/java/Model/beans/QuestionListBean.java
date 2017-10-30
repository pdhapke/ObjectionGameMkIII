package Model.beans;

import Model.Question;
import java.util.Collections;
import java.util.List; 

public class QuestionListBean implements QuestionList{
	private List<Question> list; 
		
	public QuestionListBean(){};
	public QuestionListBean(List<Question> l){
		this.list = l; 
	}
	
	public int size() {
		return list.size();
	}

	public void delete(int questionID) {
		for(Question question : list){
			if (question.getQuestionID() == questionID) {
				list.remove(question);
			}
		}
		
	}

	public void add(Question question) {
		list.add(question); 
		
	}

	public void randomize() {
		Collections.shuffle(list);
	}
	
	public List<Question> getQuestionList() {
		return list;
	}

	public Question get(int i) {
		// TODO Auto-generated method stub
		return list.get(i);
	}

	
}

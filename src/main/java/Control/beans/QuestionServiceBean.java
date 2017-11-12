package Control.beans;

import java.util.List;
import java.util.ArrayList; 
import Control.QuestionService;
import Model.Question;
import Model.AuthenticatedUser;
import Model.Objection;
import Model.beans.QuestionList;

public class QuestionServiceBean implements QuestionService{
	private int score; 
	private QuestionList list; 
	private AuthenticatedUser user; 
	private List<Integer> completedQuestions; 
	private int nextQuestion; 
	private String objectionPracticeType; 
	private int numberOfQuestionsRequested;
	
	
	public QuestionServiceBean(){
		this.score = 0; 
		this.completedQuestions = new ArrayList<Integer>(); 
		this.nextQuestion = 0; 
		this.objectionPracticeType = null; 
		};
	public QuestionServiceBean(String type, int numberOfQuestions){
		this.completedQuestions = new ArrayList<Integer>(); 
		this.score = 0; 
		this.nextQuestion = 0; 
		this.objectionPracticeType = type; 
		this.numberOfQuestionsRequested = numberOfQuestions;
		//create a list with type and number of questions requested
	}
	//testing constructor
	public QuestionServiceBean(QuestionList testList, int numberOfQuestions){
		this.completedQuestions = new ArrayList<Integer>(); 
		this.list = testList; 
		this.score = 0; 	
		this.nextQuestion = 0; 
		this.numberOfQuestionsRequested = numberOfQuestions; 
		this.objectionPracticeType = null; 
	}
	public AuthenticatedUser getUser() {
		return user;
	}
	public void setUser(AuthenticatedUser user) {
		this.user = user;
	}
	public String getObjectionPracticeType() {
		return objectionPracticeType;
	}
	public void setObjectionPracticeType(String objectionPracticeType) {
		this.objectionPracticeType = objectionPracticeType;
	}	
	public void updateScore(boolean correct) {
		if (correct){
			this.score++;
		}
	}
	public void getNewList(String type, int size) {
		// TODO Auto-generated method stub
	
	}
	public int score() {
		return this.score;
	}
	public int getNumberOfQuestionsRequested() {
		return numberOfQuestionsRequested;
	}
	public void setNumberOfQuestionsRequested(int numberOfQuestionsRequested) {
		this.numberOfQuestionsRequested = numberOfQuestionsRequested;
	}
	public List<Integer> completedQuestions() {
		return this.completedQuestions;
	}

	public Question getNext() {
		return list.get(this.nextQuestion);
	}

	public Question getCurrent() {
		int index; 
		if (this.nextQuestion == 0){
			index = 0; 
		} else {
			index = this.nextQuestion - 1; 
		}
		return list.get(index);
	}

	public boolean hasNext() {
		boolean response; 
		if((this.nextQuestion >= list.size())){
			response = false; 
		} else {
			response = true; 
		}
		return response;
	}
	

	public boolean userObjectsTo(String objectionType, String objectionTime) {
		boolean correct = false; 
		Question currentQuestion = this.getCurrent(); 
		
		for(Objection obj : currentQuestion.getCorrectObjections()){
			if(obj.getObjectionType() == objectionType){
				correct = true; 
			}
		}
		completedQuestions.add(this.getCurrent().getQuestionID()); 			
		this.nextQuestion++; 
		updateScore(correct); 
		return correct;
	}

	public Objection getCorrectObjection() {
		int FIRST_POSSIBLE = 0;
		return this.getCurrent().getCorrectObjections().get(FIRST_POSSIBLE);
	}
	
	public Objection getCorrectObjection(String type) {
		int i = 0;
		while(this.getCurrent().getCorrectObjections().get(i).getObjectionType() != type){
			i++;
		}
		return this.getCurrent().getCorrectObjections().get(i);
	}
	public String toString(){
		String output = ""; 
		int i = 1; 
		output = output.concat("********").concat("\n"); 
		output = output.concat("The current score is: " + this.score + " Points").concat("\n"); 
		output = output.concat("In this round there are " + this.completedQuestions.size() + "/" + this.numberOfQuestionsRequested + " Questions completed").concat("\n"); 
		output = output.concat("The current score is: " + this.score + " Points").concat("\n"); 
		output = output.concat("The remaining questions are....").concat("\n"); 
		
		for(Question q : list.getQuestionList()){
			output = output.concat("--------" + i + "--------").concat("\n"); 
			output = output.concat(q.toString()).concat("\n");
			i++; 
		}
		if(this.objectionPracticeType != null){
			output = output.concat("\tCurrently practicing: " + this.objectionPracticeType); 
		} else {
			output = output.concat("\tCurrently practicing all objection types"); 

		}
		
		return output;		
	}
	public List<Question> getList() {
		return list.getQuestionList();
	}
	public void setList(List<Question> l) {
		this.setList(l);
	}
}

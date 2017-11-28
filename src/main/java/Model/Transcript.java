package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;

@Entity
@Table (name = "questions")
public class Transcript {
@Id
@GeneratedValue(strategy=GenerationType.SEQUENCE)
@Column(name = "question_id")
private int questionID; 
@Column(name = "witness_id")
private int fk_witnessID; 
@Column(name = "previous_question_id")
private int previousQuestionID;	
@Column(name="side")
private String sideAskingQuestion;
@Column(name="question")
private String courtQuestion; 
@Column(name="answer")
private String witnessAnswer; 
@Transient
private List<String> previousQuestion;

public String toString(){
	String s = ""; 
	int i = 1; 
	s = s.concat("The " + this.sideAskingQuestion + " asks: " +"\"" + this.courtQuestion + "\"").concat("\n");
	s = s.concat("The witness will answer: " + "\"" + this.witnessAnswer + "\"").concat("\n");
	s = s.concat("*The previous question(s) were: ").concat("\n");

	if(previousQuestion.size() != 0){
		for (String string : previousQuestion){
			s = s.concat(i + " question ago: ");
			s = s.concat("\"" + string  + "\"").concat("\n");
			i++; 
		}
	}
	s = s.concat("Question ID: " + this.questionID + " Previous Question ID: " + this.previousQuestionID );
	return s;
}

public Transcript(){
	 this.previousQuestion = new ArrayList<String>();
};

public Transcript(List<String> previous, String side, String current, String answer,  int qID, int preID, int fk){
	this.previousQuestion = previous; 
	this.courtQuestion = current; 
	this.witnessAnswer = answer; 
	this.sideAskingQuestion = side;
	this.questionID = qID; 	
	this.fk_witnessID = fk; 
	this.previousQuestionID = preID;
}
public Transcript(String side, String current, String answer, int fk){
	this.courtQuestion = current; 
	this.witnessAnswer = answer; 
	this.sideAskingQuestion = side;
	this.fk_witnessID = fk; 
}

public int getPreviousQuestionID() {
	return previousQuestionID;
}
public String getSideAskingQuestion() {
	return sideAskingQuestion;
}
public void setSideAskingQuestion(String sideAskingQuestion) {
	this.sideAskingQuestion = sideAskingQuestion;
}
public void setPreviousQuestionID(int previousQuestionID) {
	this.previousQuestionID = previousQuestionID;
}
public int getFk_witnessID() {
	return fk_witnessID;
}

public void setFk_witnessID(int fk_witnessID) {
	this.fk_witnessID = fk_witnessID;
}
public List<String> getPreviousQuestion() {
	return previousQuestion;
}
public void setPreviousQuestion(List<String> previousQuestion) {
	this.previousQuestion = previousQuestion;
}
public String getCourtQuestion() {
	return courtQuestion;
}
public void setCourtQuestion(String courtQuestion) {
	this.courtQuestion = courtQuestion;
}
public String getWitnessAnswer() {
	return witnessAnswer;
}
public void setWitnessAnswer(String witnessAnswer) {
	this.witnessAnswer = witnessAnswer;
}
public int getQuestionID() {
	return questionID;
}
public void setQuestionID(int questionID) {
	this.questionID = questionID;
}
public void update(String side, String current, String answer,  int qID, int preID, int fk){
	this.courtQuestion = current; 
	this.witnessAnswer = answer; 
	this.sideAskingQuestion = side;
	this.questionID = qID; 	
	this.fk_witnessID = fk; 
	this.previousQuestionID = preID;
}
public void update(Transcript t){
	if(t.getCourtQuestion() !=null){
		this.courtQuestion = t.getCourtQuestion();	
	}
	if(t.getCourtQuestion() !=null){
		this.witnessAnswer = t.getWitnessAnswer();
	}
	if(t.getCourtQuestion() !=null){
		this.sideAskingQuestion = t.getSideAskingQuestion();
	}
	this.questionID = t.getQuestionID(); 
	this.fk_witnessID = t.getFk_witnessID(); 
	this.previousQuestionID = t.getPreviousQuestionID();
	
	}

public String getJsonString(){
	Gson json = new Gson();
	Transcript t = this; 
	String output = json.toJson(t); 
	return output;
}
public String getPastQuestionsString(){
	String text=""; 
	if(previousQuestion.size() != 0){
		for (String string : previousQuestion){
			text = string + text; 
		}
	}
	return text;
}

}
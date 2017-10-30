package Model;

import java.util.List; 

public class Transcript {
private List<String> previousQuestion;
private String sideAskingQuestion;
private String courtQuestion; 
private String witnessAnswer; 
private int questionID; 
private int fk_witnessID; 
private int previousQuestionID;

public String toString(){
	String s = ""; 
	int i = 1; 
	s = s.concat("The " + this.sideAskingQuestion + " asks: " +"\"" + this.courtQuestion + "\"").concat("\n");
	s = s.concat("The witness will answer: " + "\"" + this.witnessAnswer + "\"").concat("\n");
	s = s.concat("*The previous question(s) were: ").concat("\n");
	for (String string : previousQuestion){
		s = s.concat(i + " question ago: ");
		s = s.concat("\"" + string  + "\"").concat("\n");
		i++; 
	}
	s = s.concat("Question ID: " + this.questionID + " Previous Question ID: " + this.previousQuestionID );
	return s;
}

public Transcript(){};

public Transcript(List<String> previous, String side, String current, String answer,  int qID, int preID, int fk){
	this.previousQuestion = previous; 
	this.courtQuestion = current; 
	this.witnessAnswer = answer; 
	this.sideAskingQuestion = side;
	this.questionID = qID; 	
	this.fk_witnessID = fk; 
	this.previousQuestionID = preID;
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



}
package Model;

import java.util.List;

public class Question {
	
	private Context context;
	private Witness witness; 
	private Transcript transcript; 
	private List<Objection> correctObjections; 
	
	public Question(){};
	public Question( Context cont, Witness wit, Transcript tran, List<Objection> correct){
		this.context = cont; 
		this.witness = wit;
		this.transcript = tran;
		this.correctObjections = correct;
			
	};
	public String toString(){
		int j = 1; 
		String output = ""; 
		output = output.concat("Question:").concat("\n");
		output = output.concat(this.context.toString()).concat("\n");
		output = output.concat(this.witness.toString()).concat("\n");
		output = output.concat(this.transcript.toString()).concat("\n");
		for(Objection o : this.correctObjections){
			output = output.concat("Objection #" + j).concat("\n");
			j++;
			output = output.concat(o.toString()).concat("\n");
		}
		output = output.concat("\t --End of Record--");

		return output;
	}
	
	public int getCaseID() {
		return context.getCaseID();
	}
	public void setCaseID(int caseID) {
		context.setCaseID(caseID);
	}
	public String getContext() {
		return context.getContext();
	}
	public void setContext(String context) {
		this.context.setContext(context);
	}
	public Context getContextObject(){
		return this.context; 
	}
	public void getContextObject(Context con){
		this.context = con; 
	}
	public Witness getWitness() {
		return witness;
	}
	public void setWitness(Witness witness) {
		this.witness = witness;
	}
	public Transcript getTranscript() {
		return transcript;
	}
	public void setTranscript(Transcript transcript) {
		this.transcript = transcript;
			}
	public List<Objection> getCorrectObjections() {
		return correctObjections;
	}
	public void setCorrectObjections(List<Objection> correctObjections) {
		this.correctObjections = correctObjections;
	}
	public int getQuestionID(){
		return this.transcript.getQuestionID();
	}

}

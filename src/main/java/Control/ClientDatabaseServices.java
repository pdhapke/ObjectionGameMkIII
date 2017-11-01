package Control;
import Model.Question;
import Model.Context;
import Model.Objection;
import Model.ObjectionType;
import Model.Transcript;
import Model.Witness;
import Model.databaseInformation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence; 

public interface ClientDatabaseServices {
	
	//Get full questions
	public List<Question> getQuestions(int numberOfQuestions, List<Integer> history); 
	public List<Question> getQuestions(String type, int numberOfQuestions, List<Integer> history ); 
	public Question getQuestion(String type, List<Integer> history);
	
	//entry point for creating questions
	public Transcript getRandomTranscript(List<Integer> history); 
	public Objection getObjectionOfType(int typeID, List<Integer> history); 
	public Objection getObjectionOfType(String type, List<Integer> history); 	
	
	//read each object by id
	public Objection getObjection(int id); 
	public Transcript getTranscript(int id); 
	public Context getContext(int id); 
	public Witness getWitness(int id); 
	public ObjectionType getObjectionType(int id); 
	
	
	}

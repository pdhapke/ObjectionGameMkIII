package Control;
import Model.Question;
import Model.AuthenticatedUser;
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
	
	//full question by id
	public Question getQuestion(int id); 
	public Question getQuestion(int id, int numnberOfPrevious); 
	//read each object by id
	
	public Objection getObjection(int id);
	public Transcript getTranscript(int id); 
	public Transcript getTranscript(int id, int numberOfPrevious); 
	public Context getContext(int id); 
	public Witness getWitness(int id); 
	public ObjectionType getObjectionType(int id); 
	public AuthenticatedUser initializeAuthenticatedUser(String email, String firstname, String lastname, String id); 
	public AuthenticatedUser getAuthenticatedUser(String email); 
	
	//read by relationship and total
	public List<Context> getAllCases(); 
	public List<Witness> getAllWitnesses(); 
	public List<Witness> getWitnessesByCaseId(int caseID); 
	public List<Objection> getAllObjections();
	public List<Objection> getObjectionsByQuestion(int questionID);
	public List<Transcript> getAllTranscripts(); 
	public List<Transcript> getAllTranscriptsForWitness(int witnessID); 
	public List<ObjectionType> getAllObjectionTypes(); 
	
	
	//check to make sure item exists
	public boolean objectionExists(int id); 
	public boolean transcriptExists(int id); 
	public boolean contextExists(int id); 
	public boolean witnessExists(int id); 
	public boolean objectionTypeExists(int id);
	}

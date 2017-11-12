package Control.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import Control.ClientDatabaseServices;
import Model.AuthenticatedUser;
import Model.Context;
import Model.Objection;
import Model.ObjectionType;
import Model.Question;
import Model.Transcript;
import Model.Witness;
import Model.databaseInformation;


public class ClientDatabaseServicesBean implements ClientDatabaseServices {
	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII");
	
	public void close(){
		emfactory.close();
	}

	public List<Question> getQuestions(int numberOfQuestions, List<Integer> history) {
		List<Question> list = new ArrayList<Question>(); 
		Witness wit; 
		Context cont; 
		List<Objection> correct; 
		String historyString = ""; 
		for (int k : history) {
			historyString = historyString.concat(", ").concat(String.valueOf(k)); 
		}
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Transcript result WHERE result.questionID NOT IN(" + historyString.substring(1) +")";
		TypedQuery<Transcript> transcriptQuery = em.createQuery(query, Transcript.class);
		List<Transcript> transcripts = transcriptQuery.getResultList();
		Collections.shuffle(transcripts);
		em.close();
		for(int i = 0; i < numberOfQuestions; i++){
			wit = getWitness(transcripts.get(i).getFk_witnessID());
			cont = getContext(wit.getFk_caseID());
			correct = getObjections(transcripts.get(i).getQuestionID());
			list.add(new Question(cont, wit, transcripts.get(i), correct));
		}
		return list;
	}
	public List<Question> getQuestions() {
		List<Question> list = new ArrayList<Question>(); 
		Witness wit; 
		Context cont; 
		List<Objection> correct; 
				
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Transcript result";
		TypedQuery<Transcript> transcriptQuery = em.createQuery(query, Transcript.class);
		List<Transcript> transcripts = transcriptQuery.getResultList();
		Collections.shuffle(transcripts);
		em.close();
		for(int i = 0; i < transcripts.size(); i++){
			wit = getWitness(transcripts.get(i).getFk_witnessID());
			cont = getContext(wit.getFk_caseID());
			correct = getObjections(transcripts.get(i).getQuestionID());
			list.add(new Question(cont, wit, transcripts.get(i), correct));
		}
		return list;
	}
	public List<Question> getQuestions(String type, int numberOfQuestions, List<Integer> history) {
		List<Question> list = new ArrayList<Question>(); 
		Witness wit; 
		Context cont; 
		List<Objection> correct; 
		String historyString = ""; 
		for (int k : history) {
			historyString = historyString.concat(", ").concat(String.valueOf(k)); 
		}
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Transcript result WHERE result.questionID NOT IN(" + historyString.substring(1) +")";
		TypedQuery<Transcript> transcriptQuery = em.createQuery(query, Transcript.class);
		List<Transcript> transcripts = transcriptQuery.getResultList();
		Collections.shuffle(transcripts);
		em.close();
		for(int i = 0; i < numberOfQuestions; i++){
			correct = getObjections(transcripts.get(i).getQuestionID());
			for (Objection objection : correct){
				if (objection.getObjectionType().equals(type)){
					wit = getWitness(transcripts.get(i).getFk_witnessID());
					cont = getContext(wit.getFk_caseID());
					list.add(new Question(cont, wit, transcripts.get(i), correct));
				}
			}
			
		}
		return list;
	}

	public Question getQuestion(String type, List<Integer> history) {
		List<Question> list = new ArrayList<Question>(); 
		Witness wit; 
		Context cont; 
		List<Objection> correct; 
		String historyString = ""; 
		for (int k : history) {
			historyString = historyString.concat(", ").concat(String.valueOf(k)); 
		}
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Transcript result WHERE result.questionID NOT IN(" + historyString.substring(1) +")";
		TypedQuery<Transcript> transcriptQuery = em.createQuery(query, Transcript.class);
		List<Transcript> transcripts = transcriptQuery.getResultList();
		Collections.shuffle(transcripts);
		em.close();
		for(int i = 0; i < transcripts.size(); i++){
			correct = getObjections(transcripts.get(i).getQuestionID());
			for (Objection objection : correct){
				if (objection.getObjectionType().equals(type)){
					wit = getWitness(transcripts.get(i).getFk_witnessID());
					cont = getContext(wit.getFk_caseID());
					list.add(new Question(cont, wit, transcripts.get(i), correct));
					break;
				}
			}
			
		}
		return list.get(0);
	}

	
	//Get information by ID
	public List<Objection> getObjections(int questionID) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Objection result WHERE result.fk_questionID =" + questionID;
		TypedQuery<Objection> typedQuery = em.createQuery(query, Objection.class);
		List<Objection> objs = typedQuery.getResultList();
		for (Objection i : objs){
			query = "Select result FROM ObjectionType result WHERE result.objectionTypeID =" + i.getFk_objectionTypeID();
			i.setDescription(em.createQuery(query, ObjectionType.class).getResultList().get(0));	
			}
		em.close();
		return objs;
	}
	public Objection getObjection(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Objection result WHERE result.objectionID =" + id;
		TypedQuery<Objection> typedQuery = em.createQuery(query, Objection.class);
		Objection obj = typedQuery.getResultList().get(0);
		query = "Select result FROM ObjectionType result WHERE result.objectionTypeID =" + obj.getFk_objectionTypeID();
		obj.setDescription(em.createQuery(query, ObjectionType.class).getResultList().get(0));
		em.close();
		
		return obj;
	}
	public Transcript getTranscript(int id) {
		return getTranscript(id, 2);
	}
	
	public Transcript getTranscript(int id, int numberOfPrevious) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		
		//get the current question
		String query = "Select result FROM Transcript result WHERE result.questionID =" + id;
		TypedQuery<Transcript> typedQuery = em.createQuery(query, Transcript.class);
		Transcript t = typedQuery.getResultList().get(0);
		
		//add the previous questions to the transcript
		int previousID = t.getPreviousQuestionID(); 
		List<String> courtRecord = new ArrayList<String>(); 
		
		for (int i = 0; i < numberOfPrevious; i++){
			
			query = "Select result FROM Transcript result WHERE result.questionID =" + previousID;
			typedQuery = em.createQuery(query, Transcript.class);
			if (!typedQuery.getResultList().isEmpty()){
				courtRecord.add(0, typedQuery.getResultList().get(0).getCourtQuestion()
							   .concat("\n")
							   .concat(typedQuery.getResultList().get(0).getWitnessAnswer())
							);
				previousID = typedQuery.getResultList().get(0).getPreviousQuestionID();
				
			}
		}
		t.setPreviousQuestion(courtRecord);
		em.close();
		return t;
	}

	public Context getContext(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Context result WHERE result.caseID = " + id;
		TypedQuery<Context> typedQuery = em.createQuery(query, Context.class);
		Context c = typedQuery.getResultList().get(0);
		em.close();
		return c;
	}

	public Witness getWitness(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Witness result WHERE result.witnessID =" + id;
		TypedQuery<Witness> typedQuery = em.createQuery(query, Witness.class);
		Witness wit = typedQuery.getResultList().get(0);
		em.close();
		return wit; 
	}

	public ObjectionType getObjectionType(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM ObjectionType result WHERE result.objectionTypeID =" + id;
		TypedQuery<ObjectionType> typedQuery = em.createQuery(query, ObjectionType.class);
		ObjectionType type = typedQuery.getResultList().get(0);
	
		em.close();
		return type; 
	
	}
	public AuthenticatedUser getAuthenticatedUser(String email, String firstname, String lastname) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		
		String query = "Select result FROM AuthenticatedUser result WHERE result.email = \"" + email + "\"";
		TypedQuery<AuthenticatedUser> typedQuery = em.createQuery(query, AuthenticatedUser.class);
		
		AuthenticatedUser user; 
		if (typedQuery.getResultList().size() != 0){
		user = typedQuery.getResultList().get(0); 
		} else {
		user = new AuthenticatedUser(email, firstname, lastname); 
		em.persist(user);
		em.getTransaction().commit();
		}
		em.close();
		return user; 
		
	}
	public boolean objectionExists(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Objection> item = em.createQuery("Select result from Objection result WHERE result.objectionID LIKE " + id, Objection.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		return answer;
	
	}

	public boolean transcriptExists(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Transcript> item = em.createQuery("Select result from Transcript result WHERE result.questionID LIKE " + id, Transcript.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		return answer;
		
	}

	public boolean contextExists(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Context> item = em.createQuery("Select result from Context result WHERE result.caseID LIKE " + id, Context.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		return answer;
	}

	public boolean witnessExists(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Witness> item = em.createQuery("Select result from Witness result WHERE result.witnessID LIKE " + id, Witness.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		return answer;
	}

	public boolean objectionTypeExists(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<ObjectionType> item = em.createQuery("Select result from ObjectionType result WHERE result.objectionTypeID LIKE " + id, ObjectionType.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		return answer;
	}

	
	
	
}

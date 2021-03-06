package Control.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import Model.databaseInformationLocal;



public class ClientDatabaseServicesBean implements ClientDatabaseServices {
	private databaseInformation info = new databaseInformation();
	
	final private int TWENTY_MINUTES = 1000 * 60 * 1; 
	

	public List<Question> getQuestions(int numberOfQuestions, List<Integer> history) {
		List<Question> list = new ArrayList<Question>(); 
		Witness wit; 
		Context cont; 
		List<Objection> correct; 
		Transcript tran; 
		List<Transcript> transcripts = getTranscripts(numberOfQuestions, history);
			
		int i = 0; 
		while(i < numberOfQuestions && i < transcripts.size()){
			tran = transcripts.get(i);
			wit = getWitness(tran.getFk_witnessID());
			cont = getContext(wit.getFk_caseID());
			correct = getObjectionsByQuestion(tran.getQuestionID());
			list.add(new Question(cont, wit, tran, correct));
			i++; 
		}
		
	
		return list;
	}
	
	public List<Question> getQuestions() {
		List<Question> list = new ArrayList<Question>(); 
		Witness wit; 
		Context cont; 
		List<Objection> correct; 
		List<Transcript> transcripts = getTranscripts();
		
		for(int i = 0; i < transcripts.size(); i++){
			wit = getWitness(transcripts.get(i).getFk_witnessID());
			cont = getContext(wit.getFk_caseID());
			correct = getObjectionsByQuestion(transcripts.get(i).getQuestionID());
			list.add(new Question(cont, wit, transcripts.get(i), correct));
		}
		return list;
	}
	
	public Question getQuestion(int type, List<Integer> history) {
		return getQuestions(1, type, history).get(0);
	}
	public List<Question> getQuestions( int numberOfQuestions, int type, List<Integer> history) {
		List<Question> list = new ArrayList<Question>(); 
		Witness wit; 
		Context cont; 
		List<Objection> correct; 
		Transcript tran; 
		List<Transcript> transcripts = getTranscripts(numberOfQuestions, history, type);
			
		int i = 0; 
		while(i < numberOfQuestions && i < transcripts.size()){
			tran = transcripts.get(i);
			wit = getWitness(tran.getFk_witnessID());
			cont = getContext(wit.getFk_caseID());
			correct = getObjectionsByQuestion(tran.getQuestionID());
			list.add(new Question(cont, wit, tran, correct));
			i++; 
		}
		
	
		return list;
	}
	
	//Get information by ID
	public List<Objection> getObjectionsByQuestion(int questionID) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
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
		emfactory.close();
		
		return objs;
	}
	public Objection getObjection(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Objection result WHERE result.objectionID =" + id;
		TypedQuery<Objection> typedQuery = em.createQuery(query, Objection.class);
		Objection obj = typedQuery.getResultList().get(0);
		query = "Select result FROM ObjectionType result WHERE result.objectionTypeID =" + obj.getFk_objectionTypeID();
		obj.setDescription(em.createQuery(query, ObjectionType.class).getResultList().get(0));
		em.close();
		emfactory.close();
		return obj;
	}
	
	public List<Transcript> getTranscripts(){
		List<Integer> history = new ArrayList<Integer>();
		history.add(0);
		return getTranscripts(10, history, -1, 2); 
	}
	public List<Transcript> getTranscripts(List<Integer> history){
		return getTranscripts(10, history, -1, 2); 
	}
	
	public List<Transcript> getTranscripts(int number,  List<Integer> history){
		return getTranscripts(number, history, -1, 2); 
	}
	public List<Transcript> getTranscripts(int number,  List<Integer> history, int type){
		return getTranscripts(number, history, type, 2); 
	}
	
	public List<Transcript> getTranscripts(int number,  List<Integer> history, int type, int numberOfPrevious){
		List<Transcript> list = new ArrayList<Transcript>();
		history.add(0);
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query; 
		TypedQuery<Transcript> transcriptQuery;
		List<Transcript> transcripts;

		if(type == -1){
			query = "Select result FROM Transcript result WHERE result.questionID NOT IN :hist";
			transcriptQuery = em.createQuery(query, Transcript.class);
			transcriptQuery.setParameter("hist", history);
			transcripts = transcriptQuery.getResultList();
		} else {
			transcripts = new ArrayList<Transcript>();
			List<Objection> subquery = getAllObjectionsByType(type);
			
			List<Integer> availibleQuestions = new ArrayList<Integer>(); 
			for(Objection item : subquery){
				availibleQuestions.add(item.getFk_questionID());
			}
			query = "Select result FROM Transcript result WHERE result.questionID NOT IN :hist";
			transcriptQuery = em.createQuery(query, Transcript.class);
			transcriptQuery.setParameter("hist", history);
			List<Transcript> transcriptsNoObjection = transcriptQuery.getResultList();
			Collections.shuffle(transcriptsNoObjection);
			
			query = "Select result FROM Transcript result WHERE result.questionID NOT IN :hist AND result.questionID IN :avail";
			transcriptQuery = em.createQuery(query, Transcript.class);
			transcriptQuery.setParameter("hist", history);
			transcriptQuery.setParameter("avail", availibleQuestions);
			List<Transcript> transcriptsWithObjection = transcriptQuery.getResultList();
			Collections.shuffle(transcriptsWithObjection);
			
			
			
			for(int x=0; x < transcriptsWithObjection.size(); x++){
				for(int y=0; y < transcriptsWithObjection.size(); y++){
					if(transcriptsWithObjection.get(x).getQuestionID() == transcriptsNoObjection.get(y).getQuestionID()){
						transcriptsNoObjection.remove(y);
						y--;
					}
				}
			}
			
			
			int i = 0; 
			int  n; 
			Random rand = new Random();
			while(i < number && ( transcriptsNoObjection.size() != 0 || transcriptsWithObjection.size() != 0)){
				n = rand.nextInt(1) + 1;
				System.out.println(n);
						
				if((n==1 && !transcriptsWithObjection.isEmpty() && transcriptsNoObjection.isEmpty())){
					transcripts.add(transcriptsWithObjection.get(0));
					transcriptsWithObjection.remove(0);
				} else if (!transcriptsNoObjection.isEmpty()) {
					transcripts.add(transcriptsNoObjection.get(0));
					transcriptsNoObjection.remove(0);
				} else if (!transcriptsWithObjection.isEmpty()){
					transcripts.add(transcriptsWithObjection.get(0));
					transcriptsWithObjection.remove(0);
				}
				i++;
			}

		}
				
		Collections.shuffle(transcripts);
		
		int i = 0; 
		Transcript t; 
		int previousID; 
		TypedQuery<Transcript> typedQuery;
		List<String> courtRecord; 
		while(i < number && i < transcripts.size()){
			t = transcripts.get(i); 
			previousID = t.getPreviousQuestionID();
			courtRecord = new ArrayList<String>();
			for (int k = 0; k < numberOfPrevious; k++){
				query = "Select result FROM Transcript result WHERE result.questionID =" + previousID;
				typedQuery = em.createQuery(query, Transcript.class);
				
				if (!typedQuery.getResultList().isEmpty()){
					courtRecord.add(0, typedQuery.getResultList().get(0).getCourtQuestion()
								   .concat("\n")
								   .concat(typedQuery.getResultList().get(0).getWitnessAnswer())
								);
					previousID = typedQuery.getResultList().get(0).getPreviousQuestionID();
					
				} else {
					k = numberOfPrevious; 
				}
			}
			t.setPreviousQuestion(courtRecord);
			list.add(t);
			i++;
		}
		em.close();
		emfactory.close();
	return list;
	}
	
	public Transcript getTranscript(int id) {
		return getTranscript(id, 2);
	}
	
	public Transcript getTranscript(int id, int numberOfPrevious) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
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
		emfactory.close();
		
		return t;
	}

	public Context getContext(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Context result WHERE result.caseID = " + id;
		TypedQuery<Context> typedQuery = em.createQuery(query, Context.class);
		Context c = typedQuery.getResultList().get(0);
		em.close();
		emfactory.close();
		return c;
	}

	public Witness getWitness(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM Witness result WHERE result.witnessID =" + id;
		TypedQuery<Witness> typedQuery = em.createQuery(query, Witness.class);
		Witness wit = typedQuery.getResultList().get(0);
		em.close();
		emfactory.close();
		return wit; 
	}

	public ObjectionType getObjectionType(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "Select result FROM ObjectionType result WHERE result.objectionTypeID =" + id;
		TypedQuery<ObjectionType> typedQuery = em.createQuery(query, ObjectionType.class);
		ObjectionType type = typedQuery.getResultList().get(0);
		em.close();
		emfactory.close();
		
		return type; 
	
	}
	public AuthenticatedUser initializeAuthenticatedUser(String email, String firstname, String lastname, String id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
			
		AuthenticatedUser user = em.find(AuthenticatedUser.class, email); 
		if (user != null){
		user.setSession_id(id);
		Date exp = new java.sql.Date(Calendar.getInstance().getTimeInMillis());; 
		user.setSession_exp(exp);
		em.getTransaction().commit();
		} else {
		user = new AuthenticatedUser(email, firstname, lastname); 
		Date exp = new java.sql.Date(Calendar.getInstance().getTimeInMillis() + TWENTY_MINUTES); 
		user.setSession_exp(exp);
		user.setSession_id(id);
		em.persist(user);
		em.getTransaction().commit();
		}
		em.close();
		emfactory.close();		
		
		return user; 
		
	}
	public AuthenticatedUser getAuthenticatedUser(String email){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		AuthenticatedUser user = em.find(AuthenticatedUser.class, email); 
		emfactory.close();
		return user;
	}
	
	public boolean objectionExists(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Objection> item = em.createQuery("Select result from Objection result WHERE result.objectionID LIKE " + id, Objection.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		emfactory.close();
		
		return answer;
	
	}

	public boolean transcriptExists(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Transcript> item = em.createQuery("Select result from Transcript result WHERE result.questionID LIKE " + id, Transcript.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		emfactory.close();
		
		return answer;
		
	}

	public boolean contextExists(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Context> item = em.createQuery("Select result from Context result WHERE result.caseID=" + id, Context.class).getResultList();
		
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		emfactory.close();
		return answer;
	}

	public boolean witnessExists(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Witness> item = em.createQuery("Select result from Witness result WHERE result.witnessID LIKE " + id, Witness.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		emfactory.close();
		return answer;
	}

	public boolean objectionTypeExists(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<ObjectionType> item = em.createQuery("Select result from ObjectionType result WHERE result.objectionTypeID LIKE " + id, ObjectionType.class).getResultList();
		boolean answer = false;
		if (item.size() >= 1 ){
			answer = true;
		}
		em.close();
		emfactory.close();
		
		return answer;
	}

	public List<Context> getAllCases() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Context> list = em.createQuery("Select result from Context result", Context.class).getResultList();
		em.close();
		emfactory.close();
		return list;
		
	}

	public List<Witness> getWitnessesByCaseId(int caseID) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Witness> list = em.createQuery("Select result from Witness result WHERE result.fk_caseID =" + caseID, Witness.class).getResultList();
		em.close();
		emfactory.close();
		return list;
	}

	public List<Transcript> getAllTranscriptsForWitness(int witnessID) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Transcript> query = em.createQuery("Select result from Transcript result WHERE result.fk_witnessID = :witID", Transcript.class);
		System.out.println(witnessID);
		query.setParameter("witID", new Long(witnessID));
		System.out.println("query made");
		List<Transcript> list = query.getResultList();
		System.out.print(list.toString());
		em.close();
		emfactory.close();
		return list;
	}

	public List<ObjectionType> getAllObjectionTypes() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<ObjectionType> list = em.createQuery("Select result from ObjectionType result", ObjectionType.class).getResultList();
		em.close();
		emfactory.close();
		return list;
	}

	public Question getQuestion(int id, int numberOfPrevious) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		
		Transcript tran = getTranscript(id, numberOfPrevious);
		Witness wit = getWitness(tran.getFk_witnessID()); 
		Context cont = getContext(wit.getFk_caseID()); 
		List<Objection> correct = getObjectionsByQuestion(id); 
		em.close();
		emfactory.close();
		return  new Question(cont, wit, tran, correct);
		
	}
	public Question getQuestion(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		
		Transcript tran = getTranscript(id);
		Witness wit = getWitness(tran.getFk_witnessID()); 
		Context cont = getContext(wit.getFk_caseID()); 
		List<Objection> correct = getObjectionsByQuestion(id); 
		em.close();
		emfactory.close();
		return  new Question(cont, wit, tran, correct);
		
	}

	public List<Witness> getAllWitnesses() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Witness> list = em.createQuery("Select result from Witness result", Witness.class).getResultList();
		em.close();
		emfactory.close();
		return list;
	}

	public List<Objection> getAllObjections() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Objection> list = em.createQuery("Select result from Objection result", Objection.class).getResultList();
		em.close();
		emfactory.close();
		return list;
	}

	public List<Transcript> getAllTranscripts() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Transcript> list = em.createQuery("Select result from Transcript result", Transcript.class).getResultList();
		em.close();
		emfactory.close();
		return list;
	}
	public List<Objection> getAllObjectionsByType(int typeID) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII", info.userProperties());
		
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		List<Objection> list = em.createQuery("Select result from Objection result WHERE result.fk_objectionTypeID =" + typeID, Objection.class).getResultList();
		em.close();
		emfactory.close();
		return list;
	}
		
	
}

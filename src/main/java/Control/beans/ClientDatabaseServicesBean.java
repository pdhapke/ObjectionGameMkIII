package Control.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import Control.ClientDatabaseServices;
import Model.Context;
import Model.Objection;
import Model.ObjectionType;
import Model.Question;
import Model.Transcript;
import Model.Witness;
import Model.databaseInformation;
import edu.dmacc.spring.userregistration.User;

public class ClientDatabaseServicesBean implements ClientDatabaseServices {
	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ObjectionGameMkIII");
	
	public void close(){
		emfactory.close();
	}

	public List<Question> getQuestions(int numberOfQuestions, List<Integer> history) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Question> getQuestions(String type, int numberOfQuestions, List<Integer> history) {
		// TODO Auto-generated method stub
		return null;
	}

	public Question getQuestion(String type, List<Integer> history) {
		// TODO Auto-generated method stub
		return null;
	}

	public Transcript getRandomTranscript(List<Integer> history) {
		// TODO Auto-generated method stub
		return null;
	}

	public Objection getObjectionOfType(int typeID, List<Integer> history) {
		// TODO Auto-generated method stub
		return null;
	}

	public Objection getObjectionOfType(String type, List<Integer> history) {
		// TODO Auto-generated method stub
		return null;
	}

	public Objection getObjection(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Transcript getTranscript(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Context getContext(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Witness getWitness(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectionType getObjectionType(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		String query = "select u from  u";
		
		TypedQuery<ObjectionType> typedQuery = em.createQuery(query, ObjectionType.class);
		ObjectionType type = typedQuery.getResultList().get(0);
		em.close();
		return type; 
	
	}
	
	
}

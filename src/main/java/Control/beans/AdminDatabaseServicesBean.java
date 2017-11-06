package Control.beans;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.gson.Gson;

import Model.Context;
import Model.Objection;
import Model.ObjectionType;
import Model.Question;
import Model.Transcript;
import Model.Witness;
import Control.AdminDatabaseServices;

public class AdminDatabaseServicesBean extends ClientDatabaseServicesBean implements AdminDatabaseServices {

	

	public boolean addQuestions(List<Question> list) {
		boolean success = true; 
		try{
			
			for(Question q : list){
				success = success && addQuestion(q);
			}
		} catch (Exception e) {
		success = false; 
		}
		return success; 
	}

	public boolean addQuestion(Question q) {
			boolean success = false; 
			Context c = q.getContextObject();
			if (contextExists(c.getCaseID()) != true){
				addContext(c);
			}
			
			Witness w = q.getWitness(); 
			if (witnessExists(w.getWitnessID()) != true){
				addWitness(w);
			}
			
			
			Transcript t = q.getTranscript(); 
			if (transcriptExists(t.getQuestionID())!= true){
				addTranscript(t);
				}
				
			List<Objection> objs = q.getCorrectObjections();
			for (Objection singleObj : objs){		
				if (objectionExists(singleObj.getObjectionID()) != true){
					if(objectionTypeExists(singleObj.getFk_objectionTypeID()) != true){
					addObjectionType(singleObj.getDescription());					
					}					
					addObjection(singleObj);
				}	
			}
			
			success = true; 
			
				
		return success;
	}

	public boolean addContext(Context c) {
		boolean success = false; 
		try {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		em.close();
		success = true; 
		} catch (Exception e) {
			success = false; 
		}
				
		return success;
	}

	public boolean addWitness(Witness w) {
		boolean success = false; 
		try {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(w);
		em.getTransaction().commit();
		em.close();
		success = true; 
		} catch (Exception e) {
			success = false; 
		}
				
		return success;
	}

	public boolean addTranscript(Transcript t) {
		boolean success = false; 
		try {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		em.close();
		success = true; 
		} catch (Exception e) {
			success = false; 
		}
				
		return success;
	}

	public boolean addObjection(Objection obj) {
		boolean success = false; 
		try {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		em.close();
		success = true; 
		} catch (Exception e) {
			success = false; 
		}
				
		return success;
	}

	public boolean addObjectionType(ObjectionType type) {
		boolean success = false; 
		try {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(type);
		em.getTransaction().commit();
		em.close();
		success = true; 
		} catch (Exception e) {
			success = false; 
		}
				
		return success;
	}

	
	
	
	public boolean updateQuestion(Question q) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateQuestion(Gson gson) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteQuestion(Question q) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteQuestion(int questionID) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}

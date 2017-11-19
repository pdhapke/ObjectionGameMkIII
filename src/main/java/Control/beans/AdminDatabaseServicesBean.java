package Control.beans;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.gson.Gson;

import Model.AuthenticatedUser;
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
		boolean finished = false; 
		try{
			boolean up1 = updateContext(q.getContextObject()); 
			boolean up2 =  updateWitness(q.getWitness()); 
			boolean up3 =  updateTranscript(q.getTranscript()); 
			
			boolean up4 = true; 
			boolean up5 = true; 
			for(Objection ob : q.getCorrectObjections()){
				up4 = up4 & updateObjection(ob);
				up5 = up5 & updateObjectionType(ob.getDescription());
			}
			if (up1 & up2 & up3 & up4 & up5){
				finished = true; 	
			}
			
		}catch (Error e){
			System.out.print(e.getMessage());
		}		
		return finished;
		
	}

	public boolean updateQuestion(Gson gson) {
		// TODO Auto-generated method stub		//set for future update
		return false;
	}

	public boolean deleteQuestion(Question q) {
		//not ideal to delete questions will usually crash by design
		boolean finished = false; 
		try{
			boolean up4 = true; 
			for(Objection ob : q.getCorrectObjections()){
				up4 = up4 & deleteObjection(ob);
			}
						
			boolean up3 =  deleteTranscript(q.getTranscript()); 
			boolean up2 =  deleteWitness(q.getWitness()); 
			boolean up1 = deleteContext(q.getContextObject()); 
						
			if (up1 & up2 & up3 & up4){
				finished = true; 	
			}
			
		}catch (Error e){
			System.out.print(e.getMessage());
		}		
		return finished;
	}

	public boolean deleteQuestion(int questionID) {
		// will usually crash by design
		return deleteQuestion(getQuestion(questionID));
	}

	public boolean updateContext(Context newC) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Context oldC = em.find(Context.class, newC.getCaseID()); 
		oldC.update(newC); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		
		em.close();
		return answer;
	}

	public boolean updateWitness(Witness w) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateTranscript(Transcript t) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateObjection(Objection obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateObjectionType(ObjectionType type) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteContext(Context c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteWitness(Witness w) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteTranscript(Transcript t) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteObjection(Objection obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteObjectionType(ObjectionType type) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteContext(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteWitness(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteTranscript(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteObjection(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteObjectionType(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateContext(int ID, String con) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateWitness(String fname, String lname, String aff, String side, int witID, int caseID) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateTranscript(String side, String current, String answer, int qID, int preID, int fk) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateObjection(String exp, String time, int objID, int fkQ, int fkOT) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateObjectionType(String type, String info, int typeID) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}

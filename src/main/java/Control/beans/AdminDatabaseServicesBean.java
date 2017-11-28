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
			int caseID = c.getCaseID();
			if (contextExists(c.getCaseID()) != true){
				caseID = addContext(c);
			}
			
			Witness w = q.getWitness();
			int witID = w.getWitnessID();
			if (witnessExists(w.getWitnessID()) != true){
				w.setFk_caseID(caseID);
				witID = addWitness(w);
			}
			
			
			Transcript t = q.getTranscript(); 
			int qID = t.getQuestionID(); 
			if (transcriptExists(t.getQuestionID())!= true){
				t.setFk_witnessID(witID);
				qID = addTranscript(t);
				}
				
			List<Objection> objs = q.getCorrectObjections();
			int typeID; 
			for (Objection singleObj : objs){		
				typeID = singleObj.getFk_objectionTypeID();
				if (objectionExists(singleObj.getObjectionID()) != true){
					if(objectionTypeExists(singleObj.getDescription().getObjectionTypeID()) != true){
					typeID = addObjectionType(singleObj.getDescription());					
					}	
					singleObj.setFk_questionID(qID);
					singleObj.setFk_objectionTypeID(typeID);
					addObjection(singleObj);
				}	
			}
			
			success = true; 
			
				
		return success;
	}

	public int addContext(Context c) {
		EntityManager em = emfactory.createEntityManager();
		try {
		
			em.getTransaction().begin();
			em.persist(c);
			em.getTransaction().commit();
	
		} catch (Exception e) {
		
		}finally{
			em.close();
		}
				
		return c.getCaseID();
	}

	public int addWitness(Witness w) {
		EntityManager em = emfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(w);
			em.getTransaction().commit();
		
			} catch (Exception e) {
			
		} finally{
			em.close();
		}
				
		return w.getWitnessID();
	}

	public int addTranscript(Transcript t) {
		EntityManager em = emfactory.createEntityManager();
		try {
	
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		
		} catch (Exception e) {
			
		} finally{
			em.close();
		}
				
		return t.getQuestionID();
	}

	public int addObjection(Objection obj) {
		EntityManager em = emfactory.createEntityManager();
		try {
		
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
				
		} catch (Exception e) {
		
		} finally{
			em.close();
		}
				
		return obj.getObjectionID();
	}

	public int addObjectionType(ObjectionType type) {
		EntityManager em = emfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(type);
			em.getTransaction().commit();
		} catch (Exception e) {
		
		} finally{
			em.close();
		}
				
		return type.getObjectionTypeID();
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
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Witness oldW = em.find(Witness.class, w.getWitnessID()); 
		oldW.update(w); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean updateTranscript(Transcript t) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Transcript oldT = em.find(Transcript.class, t.getQuestionID()); 
		oldT.update(t); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean updateObjection(Objection obj) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Objection oldO = em.find(Objection.class, obj.getObjectionID()); 
		oldO.update(obj); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean updateObjectionType(ObjectionType type) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		ObjectionType oldOT = em.find(ObjectionType.class, type.getObjectionTypeID()); 
		oldOT.update(type); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}
	public boolean deleteContext(Context c) {
		return deleteContext(c.getCaseID());
	}

	public boolean deleteWitness(Witness w) {
		return deleteWitness(w.getWitnessID()); 
	}

	public boolean deleteTranscript(Transcript t) {
		return deleteTranscript(t.getQuestionID()); 
		}

	public boolean deleteObjection(Objection obj) {
		return deleteObjection(obj.getObjectionID()); 
		}

	public boolean deleteObjectionType(ObjectionType type) {
		return deleteObjectionType(type.getObjectionTypeID());
	}

	public boolean deleteContext(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Context c = em.find(Context.class, id); 
		em.remove(c);
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean deleteWitness(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Witness w = em.find(Witness.class, id); 
		em.remove(w);
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean deleteTranscript(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Transcript t = em.find(Transcript.class, id); 
		em.remove(t);
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean deleteObjection(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Objection obj = em.find(Objection.class, id); 
		em.remove(obj);
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean deleteObjectionType(int id) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		ObjectionType type = em.find(ObjectionType.class, id); 
		em.remove(type);
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean updateContext(int ID, String con) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Context oldC = em.find(Context.class, ID); 
		oldC.update(ID, con); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		
		em.close();
		return answer;
	}

	public boolean updateWitness(String fname, String lname, String aff, String side, int witID, int caseID) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Witness oldW = em.find(Witness.class, witID); 
		oldW.update( fname, lname, aff, side, witID, caseID); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean updateTranscript(String side, String current, String answer, int qID, int preID, int fk) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean reply = false; 
		try{	
		Transcript oldT = em.find(Transcript.class, qID); 
		oldT.update(side, current, answer, qID, preID, fk); 
		em.getTransaction().commit();
		reply = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return reply;
	}

	public boolean updateObjection(String exp, String time, int objID, int fkQ, int fkOT) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		Objection oldO = em.find(Objection.class, objID); 
		oldO.update(exp, time, objID, fkQ, fkOT); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}

	public boolean updateObjectionType(String type, String info, int typeID) {
		EntityManager em = emfactory.createEntityManager();
		em.getTransaction().begin();
		boolean answer = false; 
		try{	
		ObjectionType oldOT = em.find(ObjectionType.class, typeID); 
		oldOT.update(type, info, typeID); 
		em.getTransaction().commit();
		answer = true;
		} catch(Error e){
			System.out.println(e.getMessage());
		}
		em.close();
		return answer;
	}
}

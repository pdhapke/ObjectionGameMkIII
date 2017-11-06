package View;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Control.beans.AdminDatabaseServicesBean;
import Control.beans.BeanConfiguration;
import Control.beans.QuestionServiceBean;
import Control.AdminDatabaseServices;
import Control.QuestionService;

import Model.ObjectionType;
import Model.Context;
import Model.Question;
import Model.Objection;
import Model.Transcript;
import Model.Witness;


public class TestProgram {

	public static void main(String args[]){
		
		//Sets up the beans
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class); 
		QuestionServiceBean serve = applicationContext.getBean("service", QuestionServiceBean.class);
		AdminDatabaseServicesBean db = applicationContext.getBean("db", AdminDatabaseServicesBean.class);
		
		//tests to make sure the questions are in memory
		System.out.println(serve.toString());
	
		//adds a list of questions to the database
		boolean x = db.addQuestions(serve.getList());
		System.out.println(x);
		
	//tests pulling question information from the database using the known ids from current questions in memory.
		//obj type
		ObjectionType obType = db.getObjectionType(serve.getList().get(0).getCorrectObjections().get(0).getFk_objectionTypeID());
		System.out.println(obType.toString());
		//context
		Context c = db.getContext(serve.getList().get(0).getCaseID());
		System.out.println(c.toString());
		//objection
		Objection ob = db.getObjection(serve.getList().get(0).getCorrectObjections().get(0).getObjectionID());
		System.out.println(ob.toString());
		//transcript
		Transcript t = db.getTranscript(serve.getList().get(0).getTranscript().getQuestionID());
		System.out.println(t.toString());
		//witness
		Witness w = db.getWitness(serve.getList().get(0).getWitness().getWitnessID());
		System.out.println(w.toString());
		
	//tests pulling all questions and printing
		System.out.println(db.getQuestions().toString());
		
		
		applicationContext.close();
	}
	
	
}

package View;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Control.beans.AdminDatabaseServicesBean;
import Control.beans.BeanConfiguration;
import Control.beans.QuestionServiceBean;
import Control.AdminDatabaseServices;
import Control.QuestionService;

public class TestProgram {

	public static void main(String args[]){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class); 
		QuestionServiceBean serve = applicationContext.getBean("service", QuestionServiceBean.class);
		AdminDatabaseServicesBean db = applicationContext.getBean("db", AdminDatabaseServicesBean.class);
		System.out.println(serve.toString());
		
		db.addContext(serve.getList().get(0).getContextObject());
		db.addWitness(serve.getList().get(0).getWitness());
		db.addTranscript(serve.getList().get(0).getTranscript());
		db.addObjectionType(serve.getList().get(0).getCorrectObjections().get(0).getDescription());
		db.addObjection(serve.getList().get(0).getCorrectObjections().get(0));
		
		
		db.addQuestions(serve.getList());
		applicationContext.close();
	}
	
	
}

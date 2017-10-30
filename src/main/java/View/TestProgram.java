package View;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import Control.beans.BeanConfiguration;
import Control.beans.QuestionServiceBean;
import Control.QuestionService;

public class TestProgram {

	public static void main(String args[]){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class); 
		QuestionService serve = applicationContext.getBean("service", QuestionServiceBean.class);
		System.out.println(serve.toString());
		applicationContext.close();
	}
	
	
}

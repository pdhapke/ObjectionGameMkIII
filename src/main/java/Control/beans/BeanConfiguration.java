package Control.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import Model.beans.QuestionListBean;


@Configuration
public class BeanConfiguration {
	
	@Bean
	public QuestionListBean makeQuestionList(){
		testQuestionList questionMaker= new testQuestionList(); 
		QuestionListBean bean = new QuestionListBean(questionMaker.list); 
		return bean; 
	}
	
	@Bean
	public QuestionServiceBean service(){
		QuestionServiceBean bean = new QuestionServiceBean(makeQuestionList(), 8); 
		return bean; 
	}
	
	
}

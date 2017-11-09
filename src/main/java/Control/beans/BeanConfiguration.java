package Control.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Control.beans.AdminDatabaseServicesBean;
import Model.beans.QuestionListBean;
import Model.databaseInformation;
import Model.databaseInformationLocal;

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
		QuestionServiceBean bean = new QuestionServiceBean(makeQuestionList(), 2); 
		return bean; 
	}
	
	@Bean
	public AdminDatabaseServicesBean db(){
		AdminDatabaseServicesBean bean = new AdminDatabaseServicesBean(); 
		return bean;		
	}
	@Bean 
	public databaseInformation dbInfo(){
		databaseInformation bean = new databaseInformationLocal(); 
		return bean;		
	}
	
}

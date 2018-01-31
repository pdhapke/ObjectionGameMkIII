package Control.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Control.beans.AdminDatabaseServicesBean;
import Model.beans.QuestionListBean;
import Model.databaseInformation;
import Model.databaseInformationLocal;


//delete
@Configuration

public class BeanConfiguration  {
	/*
	@Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
        registry.addResourceHandler("/Reference/**").addResourceLocations("/Reference/");
    }
	*/
	
	
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
		databaseInformation bean = new databaseInformation(); 
		return bean;		
	}
	@Bean 
	public GoogleAuthenticatorServiceBean google(){
		GoogleAuthenticatorServiceBean bean = new GoogleAuthenticatorServiceBean(); 
		return bean;		
	}
	
	@Bean
	public ClientDatabaseServicesBean client(){
		ClientDatabaseServicesBean bean = new ClientDatabaseServicesBean(); 
		return bean;
	}
}

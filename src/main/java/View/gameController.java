package View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Control.beans.AdminDatabaseServicesBean;
import Control.beans.BeanConfiguration;
import Control.beans.QuestionServiceBean;
import Model.databaseInformation;

import java.util.List;

@Controller
public class gameController {
	//Sets up the beans
	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class); 
	QuestionServiceBean serve = applicationContext.getBean("service", QuestionServiceBean.class);
	AdminDatabaseServicesBean db = applicationContext.getBean("db", AdminDatabaseServicesBean.class);
	databaseInformation dbInfo = applicationContext.getBean("dbInfo", databaseInformation.class);
	
	@RequestMapping(value="/Welcome" )
	public  ModelAndView mainpage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Welcome");
		modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
		return modelAndView;
	}
	
	@RequestMapping(value="/all" )
	public  ModelAndView user(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("all");
		modelAndView.addObject("questions", db.getQuestions()); 
		return modelAndView;
	}
}

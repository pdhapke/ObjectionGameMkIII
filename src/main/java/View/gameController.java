package View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import Control.beans.AdminDatabaseServicesBean;
import Control.beans.BeanConfiguration;
import Control.beans.GoogleAuthenticatorServiceBean;
import Control.beans.QuestionServiceBean;
import Model.AuthenticatedUser;
import Model.databaseInformation;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes(value ="user", types = {AuthenticatedUser.class})
public class gameController {
	//Sets up the beans
	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class); 
	QuestionServiceBean serve = applicationContext.getBean("service", QuestionServiceBean.class);
	AdminDatabaseServicesBean db = applicationContext.getBean("db", AdminDatabaseServicesBean.class);
	databaseInformation dbInfo = applicationContext.getBean("dbInfo", databaseInformation.class);
	GoogleAuthenticatorServiceBean google = applicationContext.getBean("google", GoogleAuthenticatorServiceBean.class);
	
	@RequestMapping(value="/Welcome" )
	public  ModelAndView mainpage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Welcome");
		modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
		return modelAndView;
	}
	@RequestMapping(value="/processSignIn", method = RequestMethod.POST )
	public  ModelAndView processUser(HttpServletRequest req, @RequestParam(value="idtoken") String token){
		System.out.println(token);
		HttpSession session = req.getSession(); 
		String StartingId = session.getId(); //initialize the session id
		req.changeSessionId();
		serve.setUser(google.verify(token, session.getId()));
		session.setAttribute("email",serve.getUser().getEmail() );
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
		modelAndView.addObject("user", serve.getUser()); 
			
		if(serve.getUser().isAdmin()){
			modelAndView.setViewName("adminOptions");
		} else {
			modelAndView.setViewName("playerOptions");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/all" )
	public  ModelAndView showAll(HttpServletRequest req){
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = req.getSession();
		serve.setUser(db.getAuthenticatedUser(session.getAttribute("email").toString()));
		AuthenticatedUser user = serve.getUser(); 
				
		if(user.getSession_id().equals(session.getId())){
			modelAndView.setViewName("all");
			modelAndView.addObject("questions", db.getQuestions()); 	
		} else {
			modelAndView.setViewName("Not-Authorized");
		}
				
		return modelAndView;
		}
	
	@RequestMapping(value = "/enter-new", method = RequestMethod.POST)
	public ModelAndView enterNew(HttpServletRequest req){
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = req.getSession();
		serve.setUser(db.getAuthenticatedUser(session.getAttribute("email").toString()));
		AuthenticatedUser user = serve.getUser(); 
				
		if(user.getSession_id().equals(session.getId())){
		modelAndView.setViewName("enter-new");
		modelAndView.addObject("user", user);	
		} else {
			modelAndView.setViewName("Not-Authorized");
		}
				
		return modelAndView;
	}
	
	//TEMPLATE
	//This is a template servlet for authorized access
	/*
	 
	@RequestMapping(value = "/template", method = RequestMethod.POST)
	public ModelAndView template(HttpServletRequest req){
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = req.getSession();
		serve.setUser(db.getAuthenticatedUser(session.getAttribute("email").toString()));
		AuthenticatedUser user = serve.getUser(); 
		if(user.getSession_id().equals(session.getId())){
			//add the view name and the objects needed
			modelAndView.setViewName("VIEW NAME");
		
		} else {
			//tell the user they are not authorized
			modelAndView.setViewName("Not-Authorized");
		}
		return modelAndView;
	}
	
	*/
	
}

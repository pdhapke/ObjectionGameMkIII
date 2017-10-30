package View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class gameController {
	//@Autowired UserDao dao;
	//private static final String[] countries = {"France", "United States", "Germany", "England"}; 
	
	@RequestMapping(value="/Welcome" )
	public  ModelAndView user(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Welcome");
		//modelAndView.addObject("user", new User()); 
		//modelAndView.addObject("countries", countries);
		return modelAndView;
	}
	
	
	/*@Bean
	public UserDao dao(){
		UserDao bean = new UserDao();
		return bean; 
		}
		*/
}

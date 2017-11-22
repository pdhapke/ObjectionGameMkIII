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
import Model.ObjectionType;
import Model.databaseInformation;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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
	
	interface Model {
	    public ModelAndView view(HttpServletRequest req);
	}
	
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
		//String StartingId = session.getId(); //initialize the session id
		req.changeSessionId();
		AuthenticatedUser user = google.verify(token, session.getId());
		session.setAttribute("email", user.getEmail() );
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
		modelAndView.addObject("user", user); 
			
		if(user.isAdmin()){
			modelAndView.setViewName("adminOptions");
		} else {
			modelAndView.setViewName("playerOptions");
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	public ModelAndView showAll(HttpServletRequest req){
		Model model = new Model(){
			public ModelAndView view(HttpServletRequest req){
				//build the modelAndView 
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("all");
			modelAndView.addObject("questions", db.getQuestions());
			return modelAndView; 
			}
		};
		return admin(req, model);
	}

	
	@RequestMapping(value = "/enter-new", method = RequestMethod.POST)
		public ModelAndView enterNew(HttpServletRequest req){
			Model model = new Model(){
				public ModelAndView view(HttpServletRequest req){
					ModelAndView modelAndView = new ModelAndView();
					//***build the modelAndView***
						modelAndView.setViewName("enter-new");	
						modelAndView.addObject("cases", db.getAllCases()); 
					//***     end building     ***
					return modelAndView; 
				}
			};
			return admin(req, model);
		}
	
	//get contexts
		@RequestMapping(value = "/get-context", method = RequestMethod.POST)
	public ModelAndView get_context(HttpServletRequest req){
		Model model = new Model(){
			public ModelAndView view(HttpServletRequest req){
				ModelAndView modelAndView = new ModelAndView();
				//***build the modelAndView***
					modelAndView.setViewName("get-context");
					int caseID = -1; 
					try{
						caseID = (Integer) req.getAttribute("caseID");
					} catch (NullPointerException e){
						//this is normal
					}
					if(caseID == -1){
					modelAndView.addObject("cases", db.getAllCases()); 
					} else {
						modelAndView.addObject("cases", db.getContext(caseID)); 
					}
				//***     end building     ***
				return modelAndView; 
			}
		};
		return admin(req, model);
	}
	//get witnesses
	@RequestMapping(value = "/get-witnesses", method = RequestMethod.POST)
	public ModelAndView get_witnesses(HttpServletRequest req){
		Model model = new Model(){
			public ModelAndView view(HttpServletRequest req){
				ModelAndView modelAndView = new ModelAndView();
				//***build the modelAndView***
					 modelAndView.setViewName("get-witnesses");
					 int caseID; 
					 String cID = req.getParameter("caseID"); 
					 				
					 if(cID == null || cID.equals("-1")){
						 modelAndView.addObject("witnesses", db.getAllWitnesses()); 
					 } else {
						 
						 try{
							 caseID = Integer.parseInt(cID);
							 modelAndView.addObject("witnesses", db.getWitnessesByCaseId(caseID)); 
						 } catch (NumberFormatException e){
							 System.out.println("Number Format Exception:" + cID );
							 modelAndView.addObject("witnesses", db.getAllWitnesses()); 
						 }
						
					 }
					
				//***     end building     ***
				return modelAndView; 
			}
		};
		return admin(req, model);
	}
	
	//get objection
	@RequestMapping(value = "/get-objections", method = RequestMethod.POST)
	public ModelAndView get_objections(HttpServletRequest req){
		Model model = new Model(){
			public ModelAndView view(HttpServletRequest req){
				ModelAndView modelAndView = new ModelAndView();
				//***build the modelAndView***
				 modelAndView.setViewName("get-objections");
				 int questionID; 
				 String qID = req.getParameter("questionID"); 
				 				
				 if(qID == null || qID.equals("-1")){
					 modelAndView.addObject("objections", db.getAllObjections()); 
				 } else {
					  try{
						 questionID = Integer.parseInt(qID);
						 modelAndView.addObject("objections", db.getObjectionsByQuestion(questionID)); 
					 } catch (NumberFormatException e){
						 System.out.println("Number Format Exception:" + qID );
						 modelAndView.addObject("objections", db.getAllObjections()); 
					 }
				 }
				//***     end building     ***
				return modelAndView; 
			}
		};
		return admin(req, model);
	}
	
	//get transcripts / questions
	@RequestMapping(value = "/get-questions", method = RequestMethod.POST)
	public ModelAndView get_questions(HttpServletRequest req){
		Model model = new Model(){
			public ModelAndView view(HttpServletRequest req){
				ModelAndView modelAndView = new ModelAndView();
				//***build the modelAndView***
					 modelAndView.setViewName("get-questions");
					 int witnessID = -1; 
					 try{
						 witnessID = (Integer) req.getAttribute("witnessID");
					 } catch (NullPointerException e){
						//this is normal
					 }
					 if(witnessID == -1){
						 modelAndView.addObject("questions", db.getAllTranscripts()); 
					 } else {
						 modelAndView.addObject("questions", db.getAllTranscriptsForWitness(witnessID)); 
					 }
				//***     end building     ***
				return modelAndView; 
			}
		};
		return admin(req, model);
	}
	//get objection type
	@RequestMapping(value = "/get-objectiontypes", method = RequestMethod.POST)
	public ModelAndView get_objections_types(HttpServletRequest req){
		Model model = new Model(){
			public ModelAndView view(HttpServletRequest req){
				ModelAndView modelAndView = new ModelAndView();
				//***build the modelAndView***
				 modelAndView.setViewName("get-objectiontypes");
				 int typeID; 
				 String tID = req.getParameter("typeID"); 
				 				
				 if(tID == null || tID.equals("-1")){
					 modelAndView.addObject("objectionTypes", db.getAllObjectionTypes()); 
				 } else {
					  try{
						 typeID = Integer.parseInt(tID);
						 List<ObjectionType> objectionTypes = new ArrayList<ObjectionType>();
						 objectionTypes.add(db.getObjectionType(typeID));
						 modelAndView.addObject("objectionTypes", objectionTypes); 
					 } catch (NumberFormatException e){
						 System.out.println("Number Format Exception:" + tID );
						 modelAndView.addObject("objectionTypes", db.getAllObjections()); 
					 }
				 }
				//***     end building     ***
				return modelAndView; 
			}
		};
		return admin(req, model);
	}
	
	//admin function to verify if the user is authorized before running 
	public ModelAndView admin(HttpServletRequest req, Model model){
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = req.getSession();
		AuthenticatedUser user = db.getAuthenticatedUser(session.getAttribute("email").toString());
		if(user.getSession_id().equals(session.getId())){
			if(user.isAdmin()){
				//add the view name and the objects needed
					modelAndView = model.view(req); 
					modelAndView.addObject("user", user);
				} else {
					modelAndView.setViewName("Not-Authorized");
				}
			} else {
				modelAndView.setViewName("Not-Signed-In"); 
			}
			return modelAndView;
		}
	
	/* 
	 * Admin template 
	
	 		
	//this template uses the anonymous class and method in order to avoid doing database operations 
	//until after the user has been authenticated. 

		@RequestMapping(value = "/template2", method = RequestMethod.POST)
		public ModelAndView template2(HttpServletRequest req){
			Model model = new Model(){
				public ModelAndView view(HttpServletRequest req){
					ModelAndView modelAndView = new ModelAndView();
					//***build the modelAndView***
						modelAndView.setViewName("view name");
						modelAndView.addObject("object name", object); 
					//***     end building     ***
					return modelAndView; 
				}
			};
			return admin(req, model);
		}
		
		*/
}
	

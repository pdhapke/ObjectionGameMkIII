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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Control.beans.AdminDatabaseServicesBean;
import Control.beans.BeanConfiguration;
import Control.beans.ClientDatabaseServicesBean;
import Control.beans.GoogleAuthenticatorServiceBean;
import Control.beans.QuestionServiceBean;
import Model.AuthenticatedUser;
import Model.Context;
import Model.Objection;
import Model.ObjectionType;
import Model.Question;
import Model.Transcript;
import Model.Witness;
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
	ClientDatabaseServicesBean client = applicationContext.getBean("client", ClientDatabaseServicesBean.class);
	interface Model {
	    public ModelAndView view(HttpServletRequest req);
	}
	/*
	 * General servers for all below
	 * 
	 */
	
	@RequestMapping(value="/Welcome" )
	public  ModelAndView mainpage(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Welcome");
		modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
		return modelAndView;
	}
	@RequestMapping(value="/processSignIn", method = {RequestMethod.POST, RequestMethod.GET})
	public  ModelAndView processUser(HttpServletRequest req, @RequestParam(value="idtoken") String token){
		System.out.println(token);
		HttpSession session = req.getSession(); 
		//String StartingId = session.getId(); //initialize the session id
		req.changeSessionId();
		AuthenticatedUser user = google.verify(token, session.getId());
				
		if (user == null){
			return new ModelAndView("redirect:/Welcome.mvc");
		}
		
		session.setAttribute("email", user.getEmail());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
		modelAndView.addObject("user", user); 
		modelAndView.addObject("ObjectionTypes", client.getAllObjectionTypes()); 	
		if(user.isAdmin()){
			modelAndView.setViewName("adminOptions");
		} else {
			modelAndView.setViewName("playerOptions");
		}
		
		return modelAndView;
	}
	
	/*
	 * Player and non-admin servers below
	 * 
	 */
	
	@RequestMapping(value = "/get-game-questions", method = RequestMethod.GET)
	public ModelAndView getGameQuestions(HttpServletRequest req){
		Model model = new Model(){
			
			@SuppressWarnings("unchecked")
			public ModelAndView view(HttpServletRequest req){
				//build the modelAndView 
					ModelAndView modelAndView = new ModelAndView();
					modelAndView.setViewName("get-game-questions");
					String objectionrRequestString = req.getParameter("typeID");
					String historyJSON = req.getParameter("previous"); 
					int typeID; 
					List<Integer> history; 
					List<Question> generatedQuestions; 
					if (historyJSON == null || historyJSON.equals("-1")){
						try{
							Gson gson = new GsonBuilder().create();
							history = gson.fromJson(historyJSON, ArrayList.class);
						} catch(Error e){
							history = new ArrayList<Integer>(); 
						} 
					} else {
						history = new ArrayList<Integer>(); 
					}
					
					if(objectionrRequestString == null || objectionrRequestString.equals("-1")){
						generatedQuestions = client.getQuestions();
					} else {
						try{
							typeID = Integer.parseInt(objectionrRequestString);
							generatedQuestions = client.getQuestions(typeID, history);
						} catch (NumberFormatException e){
							System.out.println("Number Format Exception:" + objectionrRequestString);
							generatedQuestions = client.getQuestions();
						}}
					Gson json = new Gson();
					String questionJSON = json.toJson(generatedQuestions); 
					modelAndView.addObject("questions", questionJSON);
				//end building
			
			return modelAndView; 
				}
			};
				
		return model.view(req);
	}
	
	
	/*
	 * Admin functions and servlets below
	 */
	
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
					 int witnessID; 
					 String wID = req.getParameter("witnessID"); 
					
					 if(wID == null || wID.equals("-1")){
						 
						 modelAndView.addObject("questions", db.getAllTranscripts()); 
					 } else {
						  try{
							 witnessID = Integer.parseInt(wID);
							 modelAndView.addObject("questions", db.getAllTranscriptsForWitness(witnessID)); 
						 } catch (NumberFormatException e){
							 System.out.println("Number Format Exception:" + wID );
							 modelAndView.addObject("questions", db.getAllTranscripts()); 
						 }
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
	
	
	
	@RequestMapping(value = "/submit-new-question", method = RequestMethod.POST)
	public ModelAndView submit_new_question(HttpServletRequest req){
		System.out.println("inside");
		Model model = new Model(){
			public ModelAndView view(HttpServletRequest req){
				ModelAndView modelAndView = new ModelAndView();
				//***build the modelAndView***
					 
					//make the context object
					int contextID;	
					Context cont = new Context(); 
					String contextText = req.getParameter("caseBrief");
					String contextIDText = req.getParameter("context");
					if(contextIDText == null || contextIDText.equals("-1")){
						//do not initialize the id so that it will be autogenerated
						cont.setContext(contextText);
						contextID = db.addContext(cont);
					} else {
						contextID = Integer.parseInt(contextIDText);
						cont.setContext(contextText);
						cont.setCaseID(contextID);
						db.updateContext(cont);
					}
					
					
					//make the witness object
					String fname = req.getParameter("witnessFirstname");
					String lname = req.getParameter("witnessLastname"); 
					String side = req.getParameter("witnessSide");
					String aff = req.getParameter("witnessAffidavit");
					Witness wit = new Witness(fname, lname, aff, side);
					wit.setFk_caseID(contextID);
					int witID;
					String witnessIDString = req.getParameter("witness"); 
					
					if(witnessIDString == null || witnessIDString.equals("-1")){
						//do not initialize the id so that it will be autogenerated
						witID = db.addWitness(wit);
					} else {
						witID = Integer.parseInt(witnessIDString);
						wit.setWitnessID(witID);
						db.updateWitness(wit);
					}
										
					//make the transcript object
					side  = req.getParameter("questionAskingSide"); 
					String current  = req.getParameter("questionAsked");
					String answer  = req.getParameter("questionWitnessAnswer");
					
					Transcript tran = new Transcript(side, current, answer, witID); 
					int preID; 
					String previousIDString;
					if(req.getParameter("questionPrevious") == null || req.getParameter("questionPrevious").equals("-1")){
						//let the database handle the null
					} else {
						previousIDString = req.getParameter("questionPrevious");
						preID = Integer.parseInt(previousIDString);
						tran.setPreviousQuestionID(preID);
					}
												
					int qID; 
					String qIDString = req.getParameter("question");
					
					if(qIDString == null || qIDString.equals("-1")){
						//do not initialize the id so that it will be autogenerated
						qID = db.addTranscript(tran);
					} else {
						qID = Integer.parseInt(witnessIDString);
						tran.setQuestionID(qID);
						db.updateTranscript(tran);
					}
					
					//make the objectionType object
					String type = req.getParameter("objectionTypeTitle");
					String info = req.getParameter("objectionTypeExplanation");
					int rule; 
					String ruleString = req.getParameter("objectionTypeRuleNumber");
					if(ruleString == null){
						rule = 0;
					} else {
						rule = Integer.parseInt(ruleString);
					}
					
					ObjectionType obType = new ObjectionType(type, info, rule);
					
					int typeID; 
					String typeIDString = req.getParameter("objectionID");
					if(typeIDString == null || typeIDString.equals("-1")){
						//do not initialize the id so that it will be autogenerated
						typeID = db.addObjectionType(obType);
					} else {
						typeID = Integer.parseInt(typeIDString);
						obType.setObjectionTypeID(typeID);
						db.updateObjectionType(obType);
					}
					
					
					//make the objection object
					String exp = req.getParameter("objectionExplanation");
					String time = req.getParameter("objectionTiming");
										
					Objection ob = new Objection(obType, exp, time, qID);
				
					//Persist the objection object
					int objID;
					String objIDString = req.getParameter("correctObjection");
					if(objIDString == null || objIDString.equals("-1")){
						//do not initialize the id so that it will be autogenerated
						objID = db.addObjection(ob);
					} else {
						objID = Integer.parseInt(objIDString);
						ob.setObjectionID(objID);
						db.updateObjection(ob);
					}
					
					Question  q = db.getQuestion(qID); 
					modelAndView.setViewName("Success-Screen");
					modelAndView.addObject("question", q); 
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
		
		if(user == null){
			return new ModelAndView("redirect:/Welcome.mvc");
		} else if(user.getSession_id().equals(session.getId())){
			if(user.isAdmin()){
				//add the view name and the objects needed
					modelAndView = model.view(req); 
					modelAndView.addObject("user", user);
				} else {
					modelAndView.setViewName("Not-Authorized");
				}
		} else {
				return new ModelAndView("redirect:/Welcome.mvc");
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
	

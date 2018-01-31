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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes(value = "user", types = { AuthenticatedUser.class })
public class gameController {
	// Sets up the beans
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

	
	@RequestMapping(value = "/preview")
	public ModelAndView testpreview() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("topbar");
		modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
		return modelAndView;
	}
	
	@RequestMapping(value = "/Welcome")
	public ModelAndView mainpage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Welcome");
		modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
		return modelAndView;
	}

	@RequestMapping(value = "/Game", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView processUser(HttpServletRequest req) {
		// , @RequestParam(value="idtoken") String token
		String token = req.getParameter("idtoken");
		ModelAndView modelAndView = new ModelAndView("redirect:/");

		if (token == null) {
			HttpSession session = req.getSession();
			if (session != null) {
				Object email = session.getAttribute("email");
				if (email != null) {
					String emailString = email.toString();
					if (emailString != null) {
						AuthenticatedUser user = db.getAuthenticatedUser(emailString);
						if (user == null) {

						} else {
							// log in with session user

							if (user.isAdmin()) {
								// add the view name and the objects needed
								modelAndView = new ModelAndView();
								modelAndView.setViewName("adminOptions");
								modelAndView.addObject("ObjectionTypes", client.getAllObjectionTypes());
								modelAndView.addObject("user", user);
							} else if (false){
								//add a check to see if the user is registered 
								modelAndView = new ModelAndView();
								modelAndView.setViewName("playerOptions");
								modelAndView.addObject("user", user);
								modelAndView.addObject("ObjectionTypes", client.getAllObjectionTypes());
								
							} else {
								// redirect user to the sign in page
								
							}
						}
					}
				}

			}
		} else {
			System.out.println(token);
			HttpSession session = req.getSession();
			req.changeSessionId();
			AuthenticatedUser user = google.verify(token, session.getId());
			if (user != null) {
				session.setAttribute("email", user.getEmail());
				modelAndView = new ModelAndView();
				modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
				modelAndView.addObject("user", user);
				modelAndView.addObject("ObjectionTypes", client.getAllObjectionTypes());
				if (user.isAdmin()) {
					modelAndView.setViewName("adminOptions");
				} else {
					modelAndView.setViewName("playerOptions");
				}
			}
		}

		return modelAndView;
	}

	/*
	 * if (token == null){ return new ModelAndView("redirect:/Welcome.mvc"); }
	 * System.out.println(token); HttpSession session = req.getSession();
	 * //String StartingId = session.getId(); //initialize the session id
	 * req.changeSessionId(); AuthenticatedUser user = google.verify(token,
	 * session.getId()); if (user == null){ return new
	 * ModelAndView("redirect:/Welcome.mvc");
	 * 
	 * }
	 * 
	 * session.setAttribute("email", user.getEmail()); ModelAndView modelAndView
	 * = new ModelAndView(); modelAndView.addObject("signIn",
	 * dbInfo.getGoogleClientID()); modelAndView.addObject("user", user);
	 * modelAndView.addObject("ObjectionTypes", client.getAllObjectionTypes());
	 * if(user.isAdmin()){ modelAndView.setViewName("adminOptions"); } else {
	 * modelAndView.setViewName("playerOptions"); }
	 * 
	 * return modelAndView;
	 */

	/*
	 * Player and non-admin servers below
	 * 
	 */

	@RequestMapping(value = "/get-game-questions")
	public ModelAndView getGameQuestions(HttpServletRequest req) {
		Model model = new Model() {

			@SuppressWarnings("unchecked")
			public ModelAndView view(HttpServletRequest req) {
				// build the modelAndView
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("get-game-questions");
				String objectionrRequestString = req.getParameter("typeID");
				String historyJSON = req.getParameter("previous");
				int typeID;
				List<Integer> history;
				List<Question> generatedQuestions;
				Gson gson = new GsonBuilder().create();
				Gson json = new Gson();
				System.out.println(historyJSON);
				System.out.println(objectionrRequestString);

				if (historyJSON == null || historyJSON.equals("-1") || historyJSON.equals("[]")) {
					history = new ArrayList<Integer>();
				} else {
					try {
						history = gson.fromJson(historyJSON, ArrayList.class);
					} catch (Error e) {
						history = new ArrayList<Integer>();
						System.out.println("History failed...");
					}
				}

				if (objectionrRequestString == null || objectionrRequestString.equals("-1")) {
					generatedQuestions = client.getQuestions();
				} else {
					try {
						typeID = Integer.parseInt(objectionrRequestString);
						generatedQuestions = client.getQuestions(10, typeID, history);
					} catch (NumberFormatException e) {
						System.out.println("Number Format Exception:" + objectionrRequestString);
						generatedQuestions = client.getQuestions();
					}
				}

				String questionJSON = json.toJson(generatedQuestions);
				modelAndView.addObject("questions", questionJSON);
				// end building

				return modelAndView;
			}
		};

		return plainUser(req, model);
	}

	/*
	 * Admin functions and servlets below
	 */

	@RequestMapping(value = "/all", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView showAll(HttpServletRequest req) {
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				// build the modelAndView
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("all");
				modelAndView.addObject("questions", db.getQuestions());
				return modelAndView;
			}
		};
		return admin(req, model, true);
	}

	@RequestMapping(value = "/enter-new", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView enterNew(HttpServletRequest req) {
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				ModelAndView modelAndView = new ModelAndView();
				// ***build the modelAndView***
				modelAndView.setViewName("enter-new");
				modelAndView.addObject("cases", db.getAllCases());
				// *** end building ***
				return modelAndView;
			}
		};
		return admin(req, model, true);
	}

	@RequestMapping(value = "/edit-old", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editOld(HttpServletRequest req) {
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				ModelAndView modelAndView = new ModelAndView();
				// ***build the modelAndView***
				modelAndView.setViewName("edit-old");
				modelAndView.addObject("cases", db.getAllCases());
				// *** end building ***
				return modelAndView;
			}
		};
		return admin(req, model, true);
	}

	// get contexts
	@RequestMapping(value = "/get-context", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView get_context(HttpServletRequest req) {
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				ModelAndView modelAndView = new ModelAndView();
				// ***build the modelAndView***
				modelAndView.setViewName("get-context");
				int caseID = -1;
				try {
					caseID = (Integer) req.getAttribute("caseID");
				} catch (NullPointerException e) {
					// this is normal
				}
				if (caseID == -1) {
					modelAndView.addObject("cases", db.getAllCases());
				} else {
					modelAndView.addObject("cases", db.getContext(caseID));
				}
				// *** end building ***
				return modelAndView;
			}
		};
		return admin(req, model);
	}

	// get witnesses
	@RequestMapping(value = "/get-witnesses", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView get_witnesses(HttpServletRequest req) {
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				ModelAndView modelAndView = new ModelAndView();
				// ***build the modelAndView***
				modelAndView.setViewName("get-witnesses");
				int caseID;
				String cID = req.getParameter("caseID");

				if (cID == null || cID.equals("-1")) {
					modelAndView.addObject("witnesses", db.getAllWitnesses());
				} else {

					try {
						caseID = Integer.parseInt(cID);
						modelAndView.addObject("witnesses", db.getWitnessesByCaseId(caseID));
					} catch (NumberFormatException e) {
						System.out.println("Number Format Exception:" + cID);
						modelAndView.addObject("witnesses", db.getAllWitnesses());
					}

				}

				// *** end building ***
				return modelAndView;
			}
		};
		return admin(req, model);
	}

	// get objection
	@RequestMapping(value = "/get-objections", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView get_objections(HttpServletRequest req) {
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				ModelAndView modelAndView = new ModelAndView();
				// ***build the modelAndView***
				modelAndView.setViewName("get-objections");
				int questionID;
				String qID = req.getParameter("questionID");

				if (qID == null || qID.equals("-1")) {
					modelAndView.addObject("objections", db.getAllObjections());
				} else {
					try {
						questionID = Integer.parseInt(qID);
						modelAndView.addObject("objections", db.getObjectionsByQuestion(questionID));
					} catch (NumberFormatException e) {
						System.out.println("Number Format Exception:" + qID);
						modelAndView.addObject("objections", db.getAllObjections());
					}
				}
				// *** end building ***
				return modelAndView;
			}
		};
		return admin(req, model);
	}

	// get transcripts / questions
	@RequestMapping(value = "/get-questions", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView get_questions(HttpServletRequest req) {
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				ModelAndView modelAndView = new ModelAndView();
				// ***build the modelAndView***

				modelAndView.setViewName("get-questions");
				int witnessID;
				String wID = req.getParameter("witnessID");

				if (wID == null || wID.equals("-1")) {

					modelAndView.addObject("questions", db.getAllTranscripts());
				} else {
					try {
						witnessID = Integer.parseInt(wID);
						modelAndView.addObject("questions", db.getAllTranscriptsForWitness(witnessID));
					} catch (NumberFormatException e) {
						System.out.println("Number Format Exception:" + wID);
						modelAndView.addObject("questions", db.getAllTranscripts());
					}
				}
				// *** end building ***
				return modelAndView;
			}
		};
		return admin(req, model);
	}

	// get objection type
	@RequestMapping(value = "/get-objectiontypes", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView get_objections_types(HttpServletRequest req) {
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				ModelAndView modelAndView = new ModelAndView();
				// ***build the modelAndView***
				modelAndView.setViewName("get-objectiontypes");
				int typeID;
				String tID = req.getParameter("typeID");
				if (tID == null || tID.equals("-1")) {

					modelAndView.addObject("objectionTypes", db.getAllObjectionTypes());
				} else {
					try {
						typeID = Integer.parseInt(tID);
						List<ObjectionType> objectionTypes = new ArrayList<ObjectionType>();
						objectionTypes.add(db.getObjectionType(typeID));
						modelAndView.addObject("objectionTypes", objectionTypes);
					} catch (NumberFormatException e) {
						System.out.println("Number Format Exception:" + tID);
						modelAndView.addObject("objectionTypes", db.getAllObjections());
					}
				}
				// *** end building ***
				return modelAndView;
			}
		};
		return admin(req, model);
	}

	@RequestMapping(value = "/submit-new-question", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView submit_new_question(HttpServletRequest req) {
		System.out.println("inside");
		Model model = new Model() {
			public ModelAndView view(HttpServletRequest req) {
				ModelAndView modelAndView = new ModelAndView();
				// ***build the modelAndView***

				// make the context object
				int contextID;
				Context cont = new Context();
				String contextText = req.getParameter("caseBrief");
				String contextIDText = req.getParameter("context");
				if (contextIDText == null || contextIDText.equals("-1")) {
					// do not initialize the id so that it will be autogenerated
					cont.setContext(contextText);
					contextID = db.addContext(cont);
				} else {
					contextID = Integer.parseInt(contextIDText);
					cont.setContext(contextText);
					cont.setCaseID(contextID);
					db.updateContext(cont);
				}

				// make the witness object
				String fname = req.getParameter("witnessFirstname");
				String lname = req.getParameter("witnessLastname");
				String side = req.getParameter("witnessSide");
				String aff = req.getParameter("witnessAffidavit");
				Witness wit = new Witness(fname, lname, aff, side);
				wit.setFk_caseID(contextID);
				int witID;
				String witnessIDString = req.getParameter("witness");

				if (witnessIDString == null || witnessIDString.equals("-1")) {
					// do not initialize the id so that it will be autogenerated
					witID = db.addWitness(wit);
				} else {
					System.out.print(witnessIDString);
					witID = Integer.parseInt(witnessIDString);
					wit.setWitnessID(witID);
					db.updateWitness(wit);
				}

				// make the transcript object
				side = req.getParameter("questionAskingSide");
				String current = req.getParameter("questionAsked");
				String answer = req.getParameter("questionWitnessAnswer");

				Transcript tran = new Transcript(side, current, answer, witID);
				int preID;
				String previousIDString;
				if (req.getParameter("questionPrevious") == null || req.getParameter("questionPrevious").equals("-1")) {
					// let the database handle the null
				} else {
					previousIDString = req.getParameter("questionPrevious");
					preID = Integer.parseInt(previousIDString);
					tran.setPreviousQuestionID(preID);
				}

				int qID;
				String qIDString = req.getParameter("question");

				if (qIDString == null || qIDString.equals("-1")) {
					// do not initialize the id so that it will be autogenerated
					qID = db.addTranscript(tran);
				} else {
					qID = Integer.parseInt(witnessIDString);
					tran.setQuestionID(qID);
					db.updateTranscript(tran);
				}

				// make the objectionType object
				String type = req.getParameter("objectionTypeTitle");
				String info = req.getParameter("objectionTypeExplanation");
				int rule;
				String ruleString = req.getParameter("objectionTypeRuleNumber");
				System.out.println(ruleString);
				if (ruleString == null) {
					rule = 0;
				} else {
					rule = Integer.parseInt(ruleString);
				}

				ObjectionType obType = new ObjectionType(type, info, rule);

				int typeID;
				String typeIDString = req.getParameter("objectionID");
				if (typeIDString == null || typeIDString.equals("-1")) {
					// do not initialize the id so that it will be autogenerated

					typeID = db.addObjectionType(obType);

				} else {
					typeID = Integer.parseInt(typeIDString);
					obType.setObjectionTypeID(typeID);
					db.updateObjectionType(obType);
				}

				// make the objection object
				String exp = req.getParameter("objectionExplanation");
				String time = req.getParameter("objectionTiming");

				Objection ob = new Objection(obType, exp, time, qID);

				// Persist the objection object
				int objID;
				String objIDString = req.getParameter("correctObjection");
				if (objIDString == null || objIDString.equals("-1")) {
					// do not initialize the id so that it will be autogenerated
					objID = db.addObjection(ob);
				} else {
					objID = Integer.parseInt(objIDString);
					ob.setObjectionID(objID);
					db.updateObjection(ob);
				}

				Question q = db.getQuestion(qID);
				modelAndView.setViewName("Success-Screen");
				modelAndView.addObject("question", q);
				// *** end building ***
				return modelAndView;
			}
		};
		return admin(req, model);
	}

	// admin function to verify if the user is authorized before running
	public ModelAndView admin(HttpServletRequest req, Model model) {
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		HttpSession session = req.getSession();
		if (session != null) {

			// get the user
			Object emailOb = session.getAttribute("email");
			if (emailOb != null) {
				String email = emailOb.toString();
				if (email != null) {
					AuthenticatedUser user = db.getAuthenticatedUser(email);
					if (user != null) {
						if (user.getSession_id().equals(session.getId())) {
							if (user.isAdmin()) {
								// add the view name and the objects needed
								modelAndView = model.view(req);
								modelAndView.addObject("user", user);
							} else {
								modelAndView.setViewName("Not-Authorized");
							}
						}
					}
				}
			}
		}
		return modelAndView;
	}
	public ModelAndView admin(HttpServletRequest req, Model model, boolean hastoken) {
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		HttpSession session = req.getSession();
		String token = req.getParameter("idtoken");
		
		if (token != null){
			req.changeSessionId();
			AuthenticatedUser user = google.verify(token, session.getId());
			if (user != null) {
				session.setAttribute("email", user.getEmail());
				modelAndView = new ModelAndView();
				modelAndView.addObject("signIn", dbInfo.getGoogleClientID());
					
				if (user.isAdmin()) {
					modelAndView = model.view(req);
					modelAndView.addObject("user", user);
				} else {
					modelAndView.addObject("user", user);
					modelAndView.addObject("ObjectionTypes", client.getAllObjectionTypes());
					modelAndView.setViewName("playerOptions");
				}	
			}
		}	
			
		return modelAndView;
	}
	
	public ModelAndView plainUser(HttpServletRequest req, Model model) {
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		HttpSession session = req.getSession();
		if (session != null) {

			// get the user
			Object emailOb = session.getAttribute("email");
			if (emailOb != null) {
				String email = emailOb.toString();
				if (email != null) {
					AuthenticatedUser user = db.getAuthenticatedUser(email);
					if (user != null) {
						if (user.getSession_id().equals(session.getId())) {
							// add the view name and the objects needed
							modelAndView = model.view(req);
							modelAndView.addObject("user", user);
						}
					}
				}
			}
		}
		return modelAndView;
	}

	//static pages
	/*
	@RequestMapping(value = "/ObjectionReference", method = RequestMethod.GET)
	   public String redirect() {
	      return "redirect:/ObjectionReference.html";
	   }

	
	
	@RequestMapping(value = "/googlefe1672b955e39eb0")
	public ModelAndView google_verification() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("googleverification");
		return modelAndView;
	}
	*/
	/*
	 * Admin template
	 * 
	 * //this template uses the anonymous class and method in order to avoid
	 * doing database operations //until after the user has been authenticated.
	 * 
	 * @RequestMapping(value = "/template2", method = RequestMethod.POST) public
	 * ModelAndView template2(HttpServletRequest req){ Model model = new
	 * Model(){ public ModelAndView view(HttpServletRequest req){ ModelAndView
	 * modelAndView = new ModelAndView(); //***build the modelAndView***
	 * modelAndView.setViewName("view name");
	 * modelAndView.addObject("object name", object); //*** end building ***
	 * return modelAndView; } }; return admin(req, model); }
	 * 
	 */
}

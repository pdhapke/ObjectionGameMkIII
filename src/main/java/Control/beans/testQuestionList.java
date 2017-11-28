package Control.beans;
import Model.*;
import java.util.List; 
import java.util.ArrayList; 


public class testQuestionList{
public List<Question> list = new ArrayList<Question>();
	
	//question 1
	{
		//requires 4 pieces Context, Witness, Transcript, List<Objection> 
		Context context1 = new Context(1, "Jimmy is being tried for murder");
		Witness witness1 = new Witness("John", "Holmes", "I was told about the murder by timmy outside a bar.", "prosecution", 1, 1); 
			
			List<String> previousQuestion1 = new ArrayList<String>();
			previousQuestion1.add("Prosecution Question: Where you there that night? Answer: I was outside the bar");
		Transcript transcript1 = new Transcript(previousQuestion1, "Prosecution",  "What did you see?", "I heard timmy saying he saw the murder", 5, 4, 1); 
		
		List<Objection> correctObjections1 = new ArrayList<Objection>();
				ObjectionType hearsay = new ObjectionType("Hearsay", "Blah Blah...rule 802", 802, 1); 
				ObjectionType ultimate = new ObjectionType("Ultimate Issue", "Blah Blah...rule 704(b)", 704, 2); 
			Objection obj1 = new Objection(hearsay, "This response is hearsay, it is an out of court statement being offered for the truth of the matter asserted", "answer", 1, 5, hearsay.getObjectionTypeID()); 
			Objection obj2 = new Objection(ultimate, "This response is going to the ultimate issue (really only experts are subject)", "answer", 2, 5, ultimate.getObjectionTypeID());
			correctObjections1.add(obj1); 
			correctObjections1.add(obj2); 
						
		Question q1 = new Question(context1, witness1, transcript1, correctObjections1); 
		list.add(q1);
	}
	
	//question 2
	{
		//requires 4 pieces Conntext, Witness, Transcript, List<Objection> 
		Context context2 = new Context(2, "Widgets inc. is being sued for negligence");
		Witness witness2 = new Witness("Samantha", "Doe", "I worked on the widget project and that product is safe", "defense", 2, 2); 
			
			List<String> previousQuestion2 = new ArrayList<String>();
			previousQuestion2.add("Defense Question: What is your name? Answer: My name is Samantha Doe");
		Transcript transcript2 = new Transcript(previousQuestion2, "Defense", "Were you negligent?", "No, of course not", 20, 19, 2); 
		
		List<Objection> correctObjections2 = new ArrayList<Objection>();
				ObjectionType lackOfFoundation = new ObjectionType("Lack of Foundation", "Blah Blah...rule 602", 602, 3); 
				ObjectionType ultimate = new ObjectionType("Ultimate Issue", "Blah Blah...rule 704(b)", 704, 2); 
			Objection obj3 = new Objection(lackOfFoundation, "This response is hearsay, it is an out of court statement being offered for the truth of the matter asserted", "question", 3, 20, lackOfFoundation.getObjectionTypeID()); 
			Objection obj4 = new Objection(ultimate, "This response is going to the ultimate issue", "question", 4, 20, ultimate.getObjectionTypeID());
			correctObjections2.add(obj3); 
			correctObjections2.add(obj4); 
						
		Question q2 = new Question(context2, witness2, transcript2, correctObjections2); 
		list.add(q2);
	}
}

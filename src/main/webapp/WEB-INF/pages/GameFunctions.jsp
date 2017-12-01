<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
/*
 * Game setup functions used to retrieve questions form the database
 */

//make some globals
 var questionElement; //this is the div with the question material
 var questionBox     //this is the box with the attorney's question
 var storyElement;   //this is the div with the affadavit
 var resetButton;    //this is the button to reset the game
 var mainDiv;        //this is the button to create the main in game panel
 var break1;         //this is a break...for later
 var witnessBox;     //this is the box for the witnesses answer
 var storyBox;       //this is the box for the affidavit
 var typeSelectQuestion;
 var typeSelectWitness;
 var questionBoxExplanation; 
 var previousQuestionBox;
 var previousQuestion;
 var objectionButtonQuestion;
 var objectionButtonWitness;
 var noObjection;
 var blankObjection; 
 var questionNumber = 0; 
 var questionList = [];
 var askedQuestions = [];
 var questionBank 
 var possibleObjections = []; 
 

function resetGame(){
	mainDiv.style.display = "none";
	resetButton.style.display = "none";
	break1.style.display = "none";
	openingPanel.style.display = "inline-table";
    questionList = []; 
    questionNumber = 0;
typeSelectQuestion = null;
typeSelectWitness = null;  
}
function showGame(){
	mainDiv.style.display = 'inline-table';
	resetButton.style.display = 'inline-table';
	break1.style.display = 'inline-table';
	openingPanel.style.display = "none";
}

function pauseGame(){
	mainDiv.style.display = "none";
	resetButton.style.display = "none";
	break1.style.display = "none";
	pausePanel.style.display = 'inline-table';
    
}
function resumeGame(){
	mainDiv.style.display = 'inline-table';
	resetButton.style.display = 'inline-table';
	break1.style.display = 'inline-table';
    pausePanel.style.display = "none";
}

function resetGame2(){
    document.body.removeChild(mainDiv);
    document.body.removeChild(resetButton);
    document.body.removeChild(break1);
    document.body.appendChild(openingPanel);
    questionList = []; 
    questionNumber = 0;
typeSelectQuestion = null;
typeSelectWitness = null;
    
}
function pauseGame2(){
    document.body.removeChild(mainDiv);
    document.body.removeChild(resetButton);
    document.body.removeChild(break1);
    document.body.appendChild(pausePanel);
}
function resumeGame2(){
    document.body.appendChild(mainDiv);
    document.body.appendChild(resetButton);
    document.body.appendChild(break1);
    document.body.removeChild(pausePanel);
}


var createQuestion = function questionCreator(q){
	 alert(q.context.context);
	this.context = q.context.context;
	this.caseID = q.context.caseID; 
	this.witness = q.witness;
	this.fullname = (this.witness.firstname + " " + this.witness.lastname);
	this.transcript = q.transcript; 
	this.correctObjections = q.correctObjections;
	
	for (let i = 0; i <  this.correctObjections.length; i++){
		if (this.correctObjections[i].timing.toLowerCase() == "question"){
				this.correctObjections[i].objectionableQuestion = true; 
				this.correctObjections[i].objectionableAnswer = false; 
			} else if (this.correctObjections[i].timing.toLowerCase() == "answer"){
				this.correctObjections[i].objectionableQuestion = false; 
				this.correctObjections[i].objectionableAnswer = true; 
			}
	}
	this.previousQuestion = function generatePreviousText(){
	let answer = "";
	for(let i = 0; i < this.transcript.previousQuestion.length; i++){
		answer = answer + "\n" + this.transcript.previousQuestion[i] + "\n";
	}
		return answer; 
	}
	this.question = function compileQuestion() {
	return	"The " + this.transcript.sideAskingQuestion + " asks the witness: \n" + this.transcript.courtQuestion; 
	}
	this.story = function createStory(){
		 let answer = ""; 
		 answer = answer + "The case: \n";
		 answer = answer + this.context;
		 answer = answer + "\n\n";
		 answer = answer + this.fullname + "\n"; 
		 answer = answer + this.witness.affidavit;
		 return answer;	 
		 };
	}

blankObjection = {
		objectionID: -1, 
		fk_objectionTypeID: -1,
		explanation: "This question is not objectionable",
		timing: "none", 
		description: {
			objectionTypeID: -1,
			objectionRuleNumber:50, 
			objectionType: "none", 
			objectionInformation: "Sometimes no objections can be made" 
		}
}

possibleObjections = []; 

<c:forEach items="${ObjectionTypes}" var="type">
	possibleObjections.push({
		objectionTypeID:${type.objectionTypeID},
		objectionRuleNumber: ${type.objectionRuleNumber}, 
		objectionType: "${type.objectionType}", 
		objectionInformation: "${type.objectionInformation}"
		})
</c:forEach>

possibleObjections.sort(function(first, second){
	return - (first.objectionRuleNumber - second.objectionRuleNumber || first.objectionType.localeCompare(second.objectionType));
}); 



var correctObjectionsList = [
'Argumentative (1101 & 103)',
'Badgering (1101 & 103)',
'Relevance (402)',
'Asked and answered (403 & 1102)',
'Narrative (403 & 104)',
'Compound (403)',
'Confuses the issues (403)',
'More Prejudicial than Probative (403)',
'Cumulative (403)',
'Improper Character evidence (404 & 608)',
'Subsequent Remedial Measures (407)',
'Compromise and Offers to Compromise (408)',
'Payment of Medical & Similar Expenses (409)',
'Inadmissibility of Pleas, Plea Discussions, and Related Statements (410)',
'Liability Insurance (411)',
'Lack of Foundation (602 & 1104)',
'Lack of Personal Knowledge (602)',
'Assumes Facts not in Evidence (602 & 1103)',
'Beyond the Scope (611 D)',
'Religious Beliefs (610)',
'Leading (611 C)',
'Speculation (701 & 1105)',
'Lack of Professional Knowledge (701)',
'Ultimate Issue (704)',
'Hearsay (802)',                                                                //1
'Unresponsive (1106)',
'Unfair Extrapolation (1107)'
 ];
 
correctObjectionsList.sort();


/**
 *Game functions and control 
 */

function startGame(objectionID){
	prepareQuestions(objectionID);                  
    showGame();  
}

function prepareQuestions(typeID){
    questionNumber = 0;
    previousJSON = JSON.stringify(askedQuestions); 
    let getRequest = new XMLHttpRequest();
	getRequest.open('GET', 'get-game-questions.mvc', true);
	getRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    getRequest.onreadystatechange = function() {
	     if (this.readyState == 4 && this.status == 200) {
	    	 questionBank.innerHTML = this.response;
			 let dataStorage = document.getElementById("dataStorage");
			 data = dataStorage.getAttribute("data-json");
			 let list = JSON.parse(data); 
			 
			 for(let i = 0; i< list.length; i++){
				questionList.push(new createQuestion(list[i]));
			 }
			
			 askNextQuestion();
		   }
			 
		 }
    	getRequest.send("typeID=" + typeID + "&previous=" +previousJSON)	 
	}

function askNextQuestion(){
	
    if(questionList[questionNumber]){
    	
    	previousQuestionBox.value = questionList[questionNumber].previousQuestion();
    	questionBox.value = questionList[questionNumber].question();
    	witnessBox.value = questionList[questionNumber].transcript.witnessAnswer;
    	storyBox.value = questionList[questionNumber].story();
    	
    } else {
        alert("You have finished all the questions in this round! \n \n The game will now reset");
        resetGame();
    }
     
}


function scoreAndUpdateWitness(){
	let type = typeSelectQuestion.value; 
	scoreAndUpdateAll(type, "answer");
}
function scoreAndUpdateQuestion(){
	let type =typeSelectQuestion.value; 
	scoreAndUpdateAll(type, "question");
    
}
function scoreAndUpdateNoObjection(){
	scoreAndUpdateAll(-1, "none");
}
function scoreAndUpdateAll(type, time = "none"){
	let objs = questionList[questionNumber].correctObjections
	let objection; 
	let correct = false; 
	if (objs.length == 0){
		correct = true; 
		objection = blankObjection; 
	} else {
		objection = questionList[questionNumber].correctObjections[0];
	}
	
		
	objs.forEach(function checkobjections(ob){
		if(ob.fk_objectionTypeID == type){
			if (ob.timing.toLowerCase() == time) {
				objection = ob; 
				correct = true; 
			}
		 
		}
		
		
	}); 
	let text; 
	
	if(correct === true){
        
	     text = randomPositive() + "\n You got the question correct and have gained a point. See the explanation below for more information on the question \n\n Explanation: \n" + objection.explanation;
	    }else {
	        text = "See the explanation below for the correct answer.  \n\n Explanation: \n" + objection.explanation;
	    }
	    
	    showResults(text); 
	    	   
	    scoreBoard.panelUpdate(correct);
	    questionNumber++;
	    askNextQuestion();
}

function showResults(text){
	//this is a stub that will be changed later to a better form
	//currently uses an alert to explain the objection and results
	alert(text); 
}

function randomPositive(){
    //add in random positive exlamations
	var temp = "Great job! ";
    
    return temp
}

/*
 * Game UI and panels
 */


function drawGame(){
//main game draw
mainDiv = document.createElement('div');
mainDiv.style.display = 'inline-table';


//this creates the div for the questions answer part
questionElement = document.createElement('div');
questionElement.style.display="inline-table";



//this adds a previous question box
previousQuestion = document.createTextNode("The previous question was:");
questionElement.appendChild(previousQuestion);

questionElement.appendChild(document.createElement("br"));

previousQuestionBox = document.createElement('textarea');

previousQuestionBox.value = "Loading";
previousQuestionBox.style.backgroundColor = "cornsilk"; 
previousQuestionBox.rows = 4;
previousQuestionBox.cols = 50;
previousQuestionBox.readOnly = true; 
questionElement.appendChild(previousQuestionBox);


questionElement.appendChild(document.createElement("br"));

questionBoxExplanation = document.createTextNode("The attorney asks the witness:" );
questionElement.appendChild(questionBoxExplanation);

questionElement.appendChild(document.createElement("br"));




questionBox = document.createElement("textarea");
questionBox.value = "Loading";
questionBox.style.backgroundColor = "silver"; 
questionBox.rows = 4;
questionBox.cols = 50;
questionBox.readOnly = true; 
questionElement.appendChild(questionBox);

objectionButtonQuestion = document.createElement('button');
objectionButtonQuestion.innerText = "Objection! The question calls for...";
objectionButtonQuestion.onclick = scoreAndUpdateQuestion;

//create the type select drop downs for use by the buttons
typeSelectQuestion = document.createElement('select');
typeSelectWitness = document.createElement('select');

questionElement.appendChild(document.createElement("br"));

questionElement.appendChild(objectionButtonQuestion);

questionElement.appendChild(typeSelectQuestion);

questionElement.appendChild(document.createElement("br"));

questionElement.appendChild(document.createElement("br"));

questionElement.appendChild(document.createTextNode("(If allowed) The witness will respond with"));
questionElement.appendChild(document.createElement("br"));


witnessBox= document.createElement("textarea");
witnessBox.value = "delete me later";
witnessBox.style.backgroundColor = "white"; 
witnessBox.rows = 4;
witnessBox.cols = 50;
witnessBox.readOnly = true; 


questionElement.appendChild(witnessBox);
questionElement.appendChild(document.createElement("br"));

objectionButtonWitness = document.createElement('button');
objectionButtonWitness.innerText = "Objection! The witness has begun to...";
objectionButtonWitness.onclick = scoreAndUpdateWitness;


questionElement.appendChild(objectionButtonWitness);

questionElement.appendChild(typeSelectWitness);

questionElement.appendChild(document.createElement("br"));
questionElement.appendChild(document.createElement("br"));

noObjection = document.createElement('button');
noObjection.innerText = "No objection to the question";
noObjection.onclick = scoreAndUpdateNoObjection;


questionElement.appendChild(noObjection);

questionElement.appendChild(document.createElement("br"));



mainDiv.appendChild(questionElement);


storyElement = document.createElement('div');
storyElement.style.position = "relative";
storyElement.style.left = "10px";

storyElement.style.display="inline-table";
storyElement.appendChild(document.createTextNode("The Witness' Statement:"));
storyElement.appendChild(document. createElement("br"));
storyBox = document.createElement("textarea");
storyBox.value = "delete me later";
storyBox.style.backgroundColor = "cornsilk"; 
storyBox.rows = 24;
storyBox.cols = 54;


storyElement.appendChild(storyBox);
mainDiv.appendChild(storyElement);

document.body.appendChild(mainDiv);

break1 = document.createElement("br");
document.body.appendChild(break1);

resetButton = document.createElement("button");
resetButton.onclick = resetGame;
resetButton.innerText = "Reset";
resetButton.style.backgroundColor="red"; 
document.body.appendChild(resetButton);

//hide the three components to start
mainDiv.style.display = "none";
resetButton.style.display = "none";
break1.style.display = "none";


//finally add the three components
document.body.appendChild(mainDiv);
document.body.appendChild(resetButton);
document.body.appendChild(break1);

}

/*
 * Render the start screen
 */


//this is the player score  panel -- migrate this to the player options
var scoreBoard={
  correct:0,
  incorrect:0, 
  panel:document.createTextNode("You have not answered any questions yet"),
  panelUpdate: function(update){
      if(update){
          scoreBoard.correct++
      }else{
          scoreBoard.incorrect++
      }
      
      scoreBoard.panel.nodeValue = "You have answered: \n " + scoreBoard.correct + " questions correctly and \n " + scoreBoard.incorrect + " questions incorrectly";
      document.body.replaceChild(scoreBoard.panel,scoreBoard.panel);
  }
}    ;

document.body.appendChild(scoreBoard.panel);
document.body.appendChild(document.createElement("br"));
document.body.appendChild(document.createElement("br"))



function makeSelectionOptions(objection){
var selectionChoicesQ=[]; 
var selectionChoicesW=[];

if(objection.objectionType == "All Objections"){
for(let x = 0; x < possibleObjections.length; x++){
 
  selectionChoicesQ[x] = document.createElement('option');
  selectionChoicesQ[x].innerText = possibleObjections[x].objectionRuleNumber + " - " + possibleObjections[x].objectionType;
  selectionChoicesQ[x].value = possibleObjections[x].objectionTypeID; 
  
  selectionChoicesW[x] = document.createElement('option');
  selectionChoicesW[x].innerText = possibleObjections[x].objectionRuleNumber + " - " + possibleObjections[x].objectionType;
  selectionChoicesW[x].value = possibleObjections[x].objectionTypeID; 
  
   typeSelectQuestion.appendChild(selectionChoicesQ[x]);
   typeSelectWitness.appendChild(selectionChoicesW[x]);
   
}
} else {
   selectionChoicesQ = document.createElement('option');
   selectionChoicesQ.innerText = objection.objectionRuleNumber + " - " + objection.objectionType;
   selectionChoicesQ.value = objection.objectionTypeID; 
   selectionChoicesQ.selected = true; 
   
   selectionChoicesW = document.createElement('option');
   selectionChoicesW.innerText = objection.objectionRuleNumber + " - " + objection.objectionType;
   selectionChoicesW.value = objection.objectionTypeID; 
   selectionChoicesW.selected = true; 
  
   typeSelectQuestion.appendChild(selectionChoicesQ);
   typeSelectWitness.appendChild(selectionChoicesW); 
   }
}




//create the opening panel
var openingPanel = document.createElement('div'); 

//create the opening text sections and add to the opening panel
var openingText = document.createElement('p');
openingText.appendChild(document.createTextNode("Please select a version of the game to begin"));
openingText.style.fontSize = "x-large";
openingPanel.appendChild(openingText);


//create the button div
var practiceButtons = document.createElement("div");

var createButton = function myButtonCreator(objection) {
	var temp = document.createElement('button');
	temp.id = "TypeID" + objection.objectionTypeID;

	temp.innerText = "I want to Practice \n " + objection.objectionType; 
	temp.onclick = function(){
 		makeSelectionOptions(objection);
  		startGame(objection.objectionTypeID);
	};  
	temp.style.margin = "3px";
	practiceButtons.appendChild(temp);  
	return document.getElementById(temp.id);
}

//this will now add all the objections in the objection list
var correctObjectionsButtons = [];
var allObjectionsButton = new createButton({
	objectionTypeID:  -1,
	objectionRuleNumber: 0, 
	objectionType: "All Objections", 
	objectionInformation:  "All Objections"
	}); 
correctObjectionsButtons.push(allObjectionsButton);

possibleObjections.forEach(function(item){
	correctObjectionsButtons.push(createButton(item)); 
});


//add the buttons to the opening panel
openingPanel.appendChild(practiceButtons);

//create the pause panel
var pausePanel = document.createElement('div'); 

//create receptical for the data transfer
questionBank = document.createElement("div");
questionBank.style.display = "none";
document.body.appendChild(questionBank); 

/* 
 * Starting the game once the functions are in place
 */
 
//add the opening panel to the webpage
document.body.appendChild(openingPanel);
//draw the rest of the game while choosing
drawGame(); 

</script>

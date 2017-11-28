<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<script>
/*
 * Game setup functions used to retrieve questions form the database
 */

//to add a question use new createQuestion(objectionType, question, witnessWouldAnswer, objectionableQuestion, objectionableAnswer, explanation, statement, newStory = undefined)
//TODO 

var createQuestion = function questioncreator(objectionType, question, witnessWouldAnswer, objectionableQuestion, objectionableAnswer, explanation, statementName, newStory = undefined, previousQuestion="No previous Question"){
 
 if (newStory){ 
     statements[statementName] = newStory;
 }
 
 this.objectionType = objectionType; 
 this.question = question; 
 this.witness = witnessWouldAnswer; 
 this.objectionableQuestion = objectionableQuestion;
 this.objectionableAnswer = objectionableAnswer;
 this.statementName = statementName; 
 this.explanation = explanation;
 this.previousQuestion = previousQuestion;
 allQuestions.push(this); 
}


var statements = {};
var allQuestions = [];
var objectionTypeList = [
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
 
objectionTypeList.sort();


var questionList = [];





/**
 *Game functions and control 
 */

function startGame(objectionName){
    document.body.removeChild(openingPanel);
    
    drawGame();                         //this will draw the game
    prepareQuestions(objectionName);    //this will prepare all the questions asked for
    askNextQuestion();                  //this will ask the first question
    
}

function askNextQuestion(){
    
    if(questionList[questionNumber]){
    previousQuestionBox.value = questionList[questionNumber].previousQuestion;
    questionBox.value = questionList[questionNumber].question;
    witnessBox.value = questionList[questionNumber].witness;
    storyBox.value = statements[questionList[questionNumber].statementName];
    } else {
        alert("You have finished all the questions in this category! \n \n The game will now reset");
        resetGame();
    }
     
}

function scoreAndUpdateWitness(){
        if(typeSelectWitness.value == questionList[questionNumber].objectionType){
    scoreAndUpdate(questionList[questionNumber].objectionableAnswer);
    } else {
        alert("this is why");
        scoreAndUpdate(false);
    }
}
function scoreAndUpdateQuestion(){
   if(typeSelectQuestion.value ==questionList[questionNumber].objectionType){
    scoreAndUpdate(questionList[questionNumber].objectionableQuestion);
    } else {
        scoreAndUpdate(false);
    }
    
}

function scoreAndUpdateNoObjection(){
   var temp = false; 
 
   if(questionList[questionNumber].objectionableAnswer === false){
    alert();
   if(questionList[questionNumber].objectionableQuestion === false){
       
       temp = true;
   } 
   }
    scoreAndUpdate(temp);
}


function randomPositive(){
    var temp = "Great job! ";
    
    return temp
}

function scoreAndUpdate(score){
    var text; 
    
    if(score === true){
        
    text = randomPositive() + "\n You got the question correct and have gained a point. See the explanation below for more information on the question \n\n Explanation: \n" + questionList[questionNumber].explanation;
    }else {
        text = "See the explanation below for the correct answer.  \n\n Explanation: \n" + questionList[questionNumber].explanation;
    }
    
    alert(text); 
    
    
   
    scoreBoard.panelUpdate(score);
    questionNumber++;
    askNextQuestion();
}

function prepareQuestions(objectionName){
    questionNumber = 0;
    if(objectionName == "All Objections"){
        questionList = allQuestions;
    }else{
    for(var k in allQuestions){
        if(allQuestions[k].objectionType == objectionName){
            questionList.push(allQuestions[k]);
        }
        
     }
    }
}




function resetGame(){
    document.body.removeChild(mainDiv);
    document.body.removeChild(resetButton);
    document.body.removeChild(break1);
    document.body.appendChild(openingPanel);
    questionList = []; 
typeSelectQuestion = null;
typeSelectWitness = null;
    
    

}






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

}

/*
 * Render the start screen
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


var questionNumber = 0; 

//this is the player score  panel
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



function makeSelectionOptions(allowedObjection){
typeSelectQuestion = document.createElement('select');

typeSelectWitness = document.createElement('select');

var selectionChoicesQ=[]; 
var selectionChoicesW=[];
if(allowedObjection == "All Objections"){
for(var x in objectionTypeList){
 
  selectionChoicesQ[x] = document.createElement('option');
  selectionChoicesQ[x].innerText = objectionTypeList[x];
   
  selectionChoicesW[x] = document.createElement('option');
  selectionChoicesW[x].innerText = objectionTypeList[x];
  
   typeSelectQuestion.appendChild(selectionChoicesQ[x]);
   typeSelectWitness.appendChild(selectionChoicesW[x]);
   
}
} else {
 selectionChoicesQ = document.createElement('option');
  selectionChoicesQ.innerText = allowedObjection;
   
  selectionChoicesW = document.createElement('option');
  selectionChoicesW.innerText = allowedObjection;
  
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

var createButton = function myButtonCreator(objectionName) {
var temp = document.createElement('button');
temp.id = objectionName;

temp.innerText = "I want to Practice \n " + objectionName; 
temp.onclick = function(){
  makeSelectionOptions(objectionName);
  startGame(objectionName);
  
  
}; 
temp.style.margin = "3px";
practiceButtons.appendChild(temp);  
return document.getElementById(objectionName)
}

//this will now add all the objections in the objection list
var objectionTypeButtons = [];
for (var i in objectionTypeList){
 objectionTypeButtons[i] = createButton(objectionTypeList[i]); 
};

var allObjectionsButton = new createButton("All Objections"); 

objectionTypeButtons.push(allObjectionsButton);


/*
//add the hearsay button
var practiceHearsay = document.createElement('button'); 
practiceHearsay.innerText = "I want to Practice \n Hearsay"; 
practiceHearsay.onclick = startGame; 
practiceHearsay.style.margin = "3px";
practiceButtons.appendChild(practiceHearsay);

//add the lack of foundation button
var practiceLoF = document.createElement('button'); 
practiceLoF.innerText = "I want to  Practice \n Lack of Foundation"; 
practiceLoF.onclick = startGame; 
practiceButtons.appendChild(practiceLoF);
practiceHearsay.style.margin = "3px";

var lackofKnowledge = createButton("Lack of Knowledge", startGame);
*/





//add the buttons to the opening panel
openingPanel.appendChild(practiceButtons);

//add the opening panel to the webpage
document.body.appendChild(openingPanel);

</script>

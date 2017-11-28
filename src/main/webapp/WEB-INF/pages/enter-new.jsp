<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css" media="screen">
  div {
  
  }
  .fulldiv {
  	list-style:none;
    display: inline-flex;
    padding:5px;
  
  }
  .inner{
  list-style:none;
  float:left;
  border: solid 1px black;
  padding:10px;
;
   
  }
  .inner:nth-child(odd) {
    background: #e0e0e0;
   
  }
  select {
  
  }

 
</style>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript">
	function getContexts(){
		  let postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'get-context.mvc');
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
					document.getElementById("casesInput").innerHTML= this.response;
		    	 }
		  }
		  postRequest.send();
		  console.log('sending...');
		}
	function getWitnesses(){
		  let postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'get-witnesses.mvc', true);
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  let caseID = document.getElementById("context").value; 
		  let caseTextBox =  document.getElementById("caseBrief");
		  if (caseID != "-1"){
			  let caseOption = document.getElementById(("context" +caseID)); 
			  let caseJSON = caseOption.getAttribute("data-json")
			  let caseObject = JSON.parse(caseJSON);
			  let caseBrief = caseObject.context; 
			  caseTextBox.value = caseBrief; 
			  caseTextBox.readOnly = true; 
		  } else {
			  caseTextBox.readOnly = false;  
		  }
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
					document.getElementById("witnessInput").innerHTML= this.response;
		    	 }
		  }
		
		  postRequest.send("caseID=" + caseID)
		  console.log('sending...');
		}
	function getQuestions(){
		  let postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'get-questions.mvc', true);
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  let witnessID = document.getElementById("witness").value; 
		  if (witnessID != "-1"){
			  let witnessOption = document.getElementById(("witness" +witnessID)); 
			  let witnessJSON = witnessOption.getAttribute("data-json")
			  let wO = JSON.parse(witnessJSON);
			  let firstname = document.getElementById("witnessFirstname"); 
			  let lastname = document.getElementById("witnessLastname"); 
			  let witnessSide = document.getElementById("witnessSide");  
			  let affidavit = document.getElementById("witnessAffidavit");
			  let side = wO.side; 
			
			  switch (side.toLowerCase()){
			  	case "defense":
				  side = "Defense";
				  break; 
			  	case "plaintiff":
				  side = "Plaintiff";
				  break; 
			  	case "prosecution": 
			  	  side = "Prosecution"; 
			  	  break; 				  
			  }
			  
			for (let i=0; i< witnessSide.length; i++){
				if (witnessSide.options[i].value != side){
					witnessSide.options[i].disabled = true; 
				} else{
					witnessSide.options[i].selected = true; 
				}
			}
			  
			  firstname.value = wO.firstname;
			  lastname.value = wO.lastname;
			  affidavit.value = wO.affidavit; 
			  firstname.readOnly = true;
			  lastname.readOnly = true;
			  witnessSide.readOnly = true;
			  affidavit.readOnly = true; 
			  
		  } else {
			  firstname.value = "";
			  lastname.value = "";
			  witnessSide.value = "";
			  affidavit.value = ""; 
			  
			  firstname.readOnly = false;
			  lastname.readOnly = false;
			  witnessSide.readOnly = false;
			  affidavit.readOnly = false; 
			  for (let i=0; i< witnessSide.length; i++){
					witnessSide.options[i].disabled = false; 
				}
		  }
		  
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
					
			    	document.getElementById("questionInput").innerHTML = this.response;
			    	let previousQSelect = document.getElementById("questionPrevious");
			    	
			    	let list = document.getElementById("question"); 
			    	for(let i = 1; i < list.options.length; i++){
			   			
			    		previousQSelect.add(list.options[i].cloneNode(true));
			    	}   		
			     }
		  }
		  postRequest.send("witnessID=" + witnessID)
		  console.log('sending...');
		} 
	
	function getObjections(){
	      let postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'get-objections.mvc', true);
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  let questionID = document.getElementById("question").value; 
		  
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
					document.getElementById("objectionInput").innerHTML= this.response;
		    	 }
		  }
		  
		 //start lock/unlock
		  if (questionID != "-1"){
			  let questionOption = document.getElementById(("question" + questionID)); 
			  let questionJSON = questionOption.getAttribute("data-json")
			  let q = JSON.parse(questionJSON);
			  
			  let question = document.getElementById("questionAsked"); 
			  let answer = document.getElementById("questionWitnessAnswer"); 
			  let questionSide = document.getElementById("questionAskingSide");  
			  let previous = document.getElementById("questionPrevious");
			  
			  let side = q.sideAskingQuestion; 
			  switch (side.toLowerCase()){
			  	case "defense":
				  side = "Defense";
				  break; 
			  	case "plaintiff":
				  side = "Plaintiff";
				  break; 
			  	case "prosecution": 
			  	  side = "Prosecution"; 
			  	  break; 				  
			  }
			  
			for (let i=0; i< questionSide.length; i++){
				if (questionSide.options[i].value != side){
					questionSide.options[i].disabled = true; 
				} else {
					questionSide.options[i].selected = true; 
				}
			}
			let previousID = q.previousQuestionID;
			for (let i=0; i< previous.length; i++){
				if (previous.options[i].value != (previousID)){
					previous.options[i].disabled = true; 
				} else {
					previous.options[i].selected = true; 
				}
			} 
			
			  question.value = q.courtQuestion;
			  answer.value = q.witnessAnswer;
			  question.readOnly = true;
			  answer.readOnly = true;
			 
			  
		  } else {
			  question.value = "";
			  answer.value = "";
			  question.readOnly = false;
			  answer.readOnly = false;
				for (let i=0; i< questionSide.length; i++){
						questionSide.options[i].disabled = false; 
				}
				for (let i=0; i< previous.length; i++){
						previous.options[i].disabled = false; 
				} 
		    			  
		  }	 
		 
		 
		 //end unlock
		  
		  postRequest.send("questionID=" + questionID)
		  console.log('sending...');
		
	}
	function getObjectionTypes(){
			
		let postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'get-objectiontypes.mvc', true);
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  let typeID = document.getElementById("correctObjection").value; 
		  
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
					document.getElementById("objectionTypeInput").innerHTML= this.response;
					let objectionSelection = document.getElementById("objectionID"); 
					for(let i = 0; i < objectionSelection.length; i++){
						if(objectionSelection.options[i].value == typeID){
							objectionSelection.options[i].selected = true; 
						}
					}
					getRule(); 
					
		    	 }
		  }
		  
		  //start lock/unlock
		  if (typeID != "-1"){
			  let objectionOption = document.getElementById(("correctObjection" + typeID)); 
			  let objectionJSON = objectionOption.getAttribute("data-json")
			  let ob = JSON.parse(objectionJSON);
			  let objectionExplanation = document.getElementById("questionAsked");
			  let objectioneTiming = document.getElementById("objectionTiming");
			  
			  let objectionRuleSection = document.getElementById("objectionTypeRuleNumber"); 
			  let objectionName = document.getElementById("objectionTypeTitle");
			  let objectionRules = document.getElementById("objectionTypeExplanation"); 
			  
		  	  let timing = ob.timing;
			  if(timing =! "question"){
				  objectionTiming.options[0].disabled = true; 
			  }else{
				  objectionTiming.options[1].disabled = true; 
		  	  }
			  objectionExplanation.value = ob.explanation;
			  objectionExplanation.readOnly = true;
			  
			  					  
			  } else {
			  objectionExplanation.value = "";
			  objectionExplanation.readOnly = false;
			  objectionTiming.options[0].disabled = false; 
			  objectionTiming.options[1].disabled = false; 
			  
			  
		  }	 
		  //end unlock
		  postRequest.send("typeID=" + typeID)
		  console.log('sending...');
		
	}

	
	function getRule(){
		let objectionSelection = document.getElementById("objectionID"); 
		let typeID = objectionSelection.value; 
		let objectionTypeOption = document.getElementById("objectionID" + typeID); 
		let objectionTypeJSON = objectionTypeOption.getAttribute("data-json");
		let oT = JSON.parse(objectionTypeJSON); 
		
		let objectionRuleSection = document.getElementById("objectionTypeRuleNumber"); 
		let objectionName = document.getElementById("objectionTypeTitle");
		let objectionRules = document.getElementById("objectionTypeExplanation"); 
		
		if(typeID == "-1"){
			for (i=0; i<objectionSelection.length;i++){
				objectionSelection.options[i].disable = false; 
			}
		objectionRuleSection.value = ""; 
		objectionName.value = ""; 
		objectionRules.value = ""; 
		objectionRuleSection.readOnly = false; 
		objectionName.readOnly = false; 
		objectionRules.readOnly = false;
			
		} else {
		
		objectionRuleSection.value = oT.objectionRuleNumber; 
		objectionName.value = oT.objectionType; 
		objectionRules.value = oT.objectionInformation; 
		
		
		for (i=0; i<objectionSelection.length;i++){
			if (objectionSelection.options[i].id != "objectionID" + typeID){
				objectionSelection.options[i].disable = true; 
			} else {
				objectionSelection.options[i].selected =true; 
			}
		}
		objectionRuleSection.readOnly = true; 
		objectionName.readOnly = true; 
		objectionRules.readOnly = true;
		}
	}
	</script>
	
	
	<title>Admin: Add to datebase</title>
	
	
</head>

<body>
	<h2>Complete the fields below and press add to add a question to the database</h2>

<p>You can choose to enter new information or you can use existing entries from the drop down menus</p>


<br>
<form action="submit-new-question.mvc" method="POST" >
  <!-- This is the case drop down -->
  <div id="casesInput"> 
  	<select id="context" name="context" onchange="getWitnesses()">
   		<option id="context-1" data-json= '{}'  value="-1"> - Enter a new case - </option>
    <c:forEach items="${cases}" var="courtcase">
    	<option 
    	value="${courtcase.caseID}"
    	data-json='${courtcase.jsonString}'
    	id="context${courtcase.caseID}"
    	>Case ID#${courtcase.caseID} - ${courtcase.context} </option>
    </c:forEach>
    </select>
  </div>
  
  <div id="Contextform" class="fulldiv" >
  		<div class="inner">
  		Enter a brief description of the case in here: <br>
  		<textarea id="caseBrief" name="caseBrief" rows="4" cols="50" placeholder="Description...." required> </textarea> 
  		</div>
  		
  </div>
  <br>
   
  
  
  <!-- This is the witness drop down -->
  <div id="witnessInput"> 
  	<select id="witness" name="witness" onchange="getQuestions()">
   		<option id="witness-1"  value="-1"> - Enter a new witness - </option>
    </select>
  </div>
  <div id="Witnessform"  class="fulldiv" >
  		<div class="inner">
  		Witness's first name:<br>
  		<input type="text" required id="witnessFirstname" name="witnessFirstname" placeholder="First Name" ><br>
  		Witness's last name:<br>
  		<input type="text" required id="witnessLastname" name="witnessLastname" placeholder="Last Name"><br>
  		Witness is called by the:<br>
  		<select required name="witnessSide" id="witnessSide" required>
  			<option value="Plaintiff" >Plaintiff</option>
  			<option value="Prosecution" >Prosecution</option>
  			<option value="Defense" >Defense</option>
  		</select>
  		</div>
  		<div class="inner">
  		Enter the witness affidavit here: <br>
  		<textarea required name="witnessAffidavit" id="witnessAffidavit" rows="10" cols="50" placeholder="My name is...."></textarea>
  		</div>
  </div>
  <br>
  
  
  
    <!-- This is the court question drop down -->
  <div id="questionInput"> 
  	<select id="question" name="question" onchange="getObjections()">
   		<option  value="-1" id="question-1" data-json= "{}"> - Enter a new question - </option>
   	
    </select>
  </div>
  <div id="Questionform"  class="fulldiv" >
  		<div id="inner" class="inner">
  		Does this question follow from a previous one? <br>
  		<select required name="questionPrevious" id="questionPrevious" required>
  			<option selected="selected"  id="questionPrevious-1" value="-1" >No</option>
  		</select>
  		<br>What side is asking the question?:<br>
  		<select required name="questionAskingSide" id="questionAskingSide" required>
  			<option value="Plaintiff" >Plaintiff</option>
  			<option value="Prosecution" >Prosecution</option>
  			<option value="Defense" >Defense</option>
  		</select>
  		</div>
  		<div class="inner">
  		Enter the question: <br>
  		<textarea id="questionAsked" name="questionAsked" rows="3" cols="20" placeholder="What is..."></textarea>
  		</div>
  		<div class="inner">
  		Enter the witness's answer: <br>
  		<textarea  id="questionWitnessAnswer" name="questionWitnessAnswer" rows="3" cols="20" placeholder="Yes,..."></textarea>
  		</div>
  </div>
  <br>
  
  
  
    
    <!-- This is the objections possible drop down -->
  <div id="objectionInput"> 
  	<select id="correctObjection" name="correctObjection" onchange="getObjectionTypes()">
   		<option data-json="{}" id="correctObjection-1" value="-1"> - Enter a new possible objection - </option>
    </select>
  </div>
  <div id="ObjectionForm"  class="fulldiv" >
  		<div class="inner">
  		Explain this objection: <br>
  		<textarea id="objectionExplanation" name="objectionExplanation" rows="4" cols="25"> test 1</textarea> 
  		</div>
  		<div class="inner">
  		Select when this objection can first be made: <br>
  		<select required name="objectionTiming" id="objectionTiming" required>
  			<option value="question" >During the question</option>
  			<option value="answer" >During the witness's answer</option>
  		</select>
  		</div>
  </div>
  <br>
  
  
  
  <!-- This is the objections rule drop down -->
  <script type="text/javascript">getObjectionTypes(); </script>
  <div id="objectionTypeInput"> 
  	<select id="objectionID" name="objectionID" onchange="getRule()">
   		<option  id="objectionID-1" value="-1"> - Enter a new type of Objection- </option>
    </select>
  </div>
  <div id="ObjectionTypeForm"  class="fulldiv" >
  		<div class="inner">
  		Enter the objection rule section number: <br>
		<input type="number" placeholder="802" id="objectionTypeRuleNumber" name="objectionTypeRuleNumber" min="1" max="2500" step="1"><br>
  		Enter the objection name: <br>
  		<input type="text" required id="objectionTypeTitle" name="objectionTypeTitle" placeholder="Hearsay"><br>
  		</div>
  		<div class="inner">
  		Enter the relevant text of the rule  <br>
  		and any applicable rule identifiers: <br>
  		<textarea id="objectionTypeExplanation" name="objectionTypeExplanation" rows="3" cols="30" placeholder="801(d)(2)(d)...."> </textarea>
  		</div>
  </div>
  <br>
   
  
  <!--  This will submit the form -->>
  <input type="submit">
</form>

<div id="formDiv" hidden="true"></div>
</body>
</html>
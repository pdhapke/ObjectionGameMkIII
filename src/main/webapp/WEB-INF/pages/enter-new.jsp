<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
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
		  
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
					document.getElementById("witnessInput").innerHTML= this.response;
		    	 }
		  }
		  //postRequest.send(JSON.stringify({caseID:caseID}));
		  postRequest.send("caseID=" + caseID)
		  console.log('sending...');
		}
	function getQuestions(){
		  let postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'get-questions.mvc', true);
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  let witnessID = document.getElementById("witness").value; 
		  
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
					document.getElementById("questionInput").innerHTML= this.response;
		    	 }
		  }
		  postRequest.send("witnessID=" + witnessID)
		  console.log('sending...');
		} 
	
	function getObjections(){
		let qID = document.getElementById("question").value; 
		let question = JSON.parse(document.getElementById(qID).getAttribute("data-json")); 
		//TO DO add in the ui element of the question so it can be read
		
		
		let postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'get-objections.mvc', true);
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  let questionID = document.getElementById("question").value; 
		  
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
					document.getElementById("objecctionInput").innerHTML= this.response;
		    	 }
		  }
		  postRequest.send("questionID=" + questionID)
		  console.log('sending...');
		
	}
	function getRule(){
		
		
	}
	
	</script>
	
	
	<title>Admin: Add to datebase</title>
	
	
</head>

<body>
	<h2>Complete the fields below and press add to add a question to the database</h2>

<p>this is to test ${user.getEmail()}</p>
<p> You are authorized!</p>

<br>
<form action="/stub" onsubmit="submitform()">
  <!-- This is the case drop down -->
  <div id="casesInput"> 
  	<select id="context" name="context" onchange="getWitnesses()">
   		<option value="-1"> - Enter a new case - </option>
    <c:forEach items="${cases}" var="courtcase">
    	<option value="${courtcase.caseID}">Case ID#${courtcase.caseID} - ${courtcase.context} </option>
    </c:forEach>
    </select>
  </div>
  <div id="newContextform" hidden="true"></div>
  
  <!-- This is the witness drop down -->
  <div id="witnessInput"> 
  	<select id="witness" name="witness" onchange="getQuestions()">
   		<option value="-1"> - Enter a new witness - </option>
    </select>
  </div>
  <div id="newWitnessform" hidden="true"></div>
  
    <!-- This is the court question drop down -->
  <div id="questionInput"> 
  	<select id="question" name="question" onchange="getObections()">
   		<option value="-1"> - Enter a new question - </option>
    </select>
  </div>
  <div id="newQuestionform" hidden="true"></div>
  
    
    <!-- This is the objections possible drop down -->
  <div id="objectionInput"> 
  	<select id="correctObjection" name="correctObjection" onchange="getRule()">
   		<option value="-1"> - Enter a new possible objection - </option>
    </select>
  </div>
  <div id="newObjectionForm" hidden="true"></div>
  
  
  <!--  This will submit the form -->>
  <input type="submit">
</form>

<div id="formDiv" hidden="true"></div>
</body>
</html>
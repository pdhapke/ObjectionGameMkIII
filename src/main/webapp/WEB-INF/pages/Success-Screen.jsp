<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<head>
<%! String pq=""; %>
<%! String objs=""; %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Success!</title>
<style type="text/css" media="screen">
.pagecontent {
margin: 50px auto 0;
position: relative;

}
</style>
<%@include file="topbar.jsp" %>
</head>

<body>
<div class='pagecontent'>
<h3>Your information was added successfully!</h3>
<form action="Game.mvc" class="inline">
    <button class="float-left submit-button" >Main Menu</button>
</form>
<p>Please check to make sure all the information is correct. </p>
	<table>
		<tr>
			<th> Case ID # </th>
			<th> Case Description</th>
			<th> Witness #  </th>
			<th> Witness Name </th>
			<th> Witness Called by: </th>
			<th> :Affidavit: </th>
			<th> Question </th>
			<th> Previous Questions </th>
			<th> Correct Objection </th>
			<th> Rule </th>
			
		</tr>
		
		
			<tr>
				<!-- Context -->
				<td>${question.caseID}</td>
				<td>${question.context}</td>
				<!--  Witness -->
				<td>${question.witness.witnessID}</td>
				<td>${question.witness.firstname } ${q.witness.lastname}</td>
				<td>${question.witness.side}</td>
				<td>${question.witness.affidavit}</td>
				<!-- Question -->
				<td>ID#${question.transcript.questionID}: ${question.transcript.sideAskingQuestion}: ${question.transcript.courtQuestion} </td>
				<td>
					<c:choose>
						<c:when test="${!question.transcript.previousQuestion.isEmpty()}">
							<c:forEach items="${question.transcript.previousQuestion}" var="i">
								${i}
	      						<br>
							</c:forEach>
							<c:out value = "${pq}"/>
						</c:when>
 							<c:otherwise>
    							(None)
    						</c:otherwise>
   					</c:choose>
				<c:out value = "${pq}"/>
				</td>
				<!-- Objections  -->
				<td>
					<c:choose>
						<c:when test="${!question.correctObjections.isEmpty()}">
							
							<c:forEach items="${question.correctObjections}" var="ob">
								During ${ob.timing} - Explanation: ${ob.explanation}
								<br>
							</c:forEach>
							
						</c:when>
 							<c:otherwise>
    							(None)
    						</c:otherwise>
   					</c:choose>
				</td>
				<!-- rule -->
				<td>
					<c:choose>
						<c:when test="${!question.correctObjections.isEmpty()}">
							
							<c:forEach items="${question.correctObjections}" var="ob">
								${ob.description.objectionRuleNumber}  - ${ob.description.objectionType} - Explanation: ${ob.description.objectionInformation}
								<br>
							</c:forEach>
							
						</c:when>
 							<c:otherwise>
    							(None)
    						</c:otherwise>
   					</c:choose>
				</td>
			</tr>
		
	
	
	</table>
	
	



</div>
</body>
</html>
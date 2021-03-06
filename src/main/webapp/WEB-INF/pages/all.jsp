<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<head>
<title>All Questions</title>
<%! String pq=""; %>
<%! String objs=""; %>
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
<form action="Game.mvc" class="inline">
    <button class="float-left submit-button" >Main Menu</button>
</form>
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
		</tr>
		<c:forEach items="${questions}" var="q">
		
			<tr>
				<!-- Context -->
				<td>${q.caseID}</td>
				<td>${q.context}</td>
				<!--  Witness -->
				<td>${q.witness.witnessID}</td>
				<td>${q.witness.firstname } ${q.witness.lastname}</td>
				<td>${q.witness.side}</td>
				<td>${q.witness.affidavit}</td>
				<!-- Question -->
				<td>ID#${q.transcript.questionID}: ${q.transcript.sideAskingQuestion}: ${q.transcript.courtQuestion} </td>
				<td>
					<c:choose>
						<c:when test="${!q.transcript.previousQuestion.isEmpty()}">
							<c:forEach items="${q.transcript.previousQuestion}" var="i">
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
						<c:when test="${!q.correctObjections.isEmpty()}">
							
							<c:forEach items="${q.correctObjections}" var="ob">
								${ob.fk_objectionTypeID}  During ${ob.timing} - Explanation: ${ob.explanation}
								<br>
							</c:forEach>
							
						</c:when>
 							<c:otherwise>
    							(None)
    						</c:otherwise>
   					</c:choose>
				</td>
			</tr>
		
		</c:forEach>
	
	</table>

</div>
</body>
</html>

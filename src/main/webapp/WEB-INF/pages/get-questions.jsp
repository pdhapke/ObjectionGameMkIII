<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select id="question" name="question" onchange="getObjections()">
   		<option value="-1"> - Enter a new question - </option>
    <c:forEach items="${questions}" var="q">
    	<option 
    	data-json='${q.jsonString}'
    	id="${q.questionID}" 
    	value="${q.questionID}">Question#${q.questionID} - ${q.sideAskingQuestion}: "${q.courtQuestion}" </option>
    </c:forEach>
</select>

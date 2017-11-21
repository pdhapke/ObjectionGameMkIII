<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select id="witness" name="witness" onchange="getQuestions()">
   		<option value="custom"> - Enter a new Witness - </option>
    <c:forEach items="${witnesses}" var="witness">
    	<option value="${witness.witnessID}">Witness#${witness.witnessID} - ${witness.name} </option>
    </c:forEach>
</select>

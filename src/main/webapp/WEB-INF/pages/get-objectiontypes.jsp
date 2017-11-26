<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select id="objectionID" name="objectionID" onchange="getRule()">
   	  <option id="objectionID-1" data-json= "{}" value="-1"> - Enter a new type of Objection - </option>
    
    <c:forEach items="${objectionTypes}" var="type">
    	<option 
    	data-json='${type.jsonString}'
    	id="objectionID${type.objectionTypeID}" 
    	value="${type.objectionTypeID}">Rule#${type.objectionRuleNumber} - ${type.objectionType} </option>
    </c:forEach>
</select>

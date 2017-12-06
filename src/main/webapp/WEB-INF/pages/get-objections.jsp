<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select id="correctObjection" name="correctObjection" onchange="getObjectionTypes()">
   		<option  id="correctObjection-1" data-json= "{}" value="-1"> - Enter a new possible objection - </option>
    <c:forEach items="${objections}" var="ob">
    	<option 
    	value="${ob.fk_objectionTypeID}"
    	id="correctObjection${ob.fk_objectionTypeID}"
    	data-json='${ob.jsonString}'
    	>Rule ${ob.description.objectionRuleNumber } : ${ob.description.objectionType} </option>
    </c:forEach>
</select>

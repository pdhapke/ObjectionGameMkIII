<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select id="correctObjection" name="correctObjection" onchange="getRule()">
   		<option value="-1"> - Enter a new possible objection - </option>
    <c:forEach items="${objections}" var="ob">
    	<option 
    	value="${ob.fk_objectionID}"
    	id="${ob.fk_objectionID}"
    	data-json='${ob.jsonString}'
    	>Rule ${ob.fk_objectionTypeID } : ${ob.objectionType} </option>
    </c:forEach>
</select>

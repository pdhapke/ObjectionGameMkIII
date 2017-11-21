<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select id="context" name="context">
   		<option value="custom"> - Enter a new case - </option>
    <c:forEach items="${cases}" var="courtcase">
    	<option value="${courtcase.caseID}">Case ID#${courtcase.caseID} - ${courtcase.context} </option>
    </c:forEach>
</select>

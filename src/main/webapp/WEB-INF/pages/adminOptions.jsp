<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<head>
<script type="text/javascript"> 



function testAuth(){
	alert("Still signed in"); 
	return true; 
}
</script>
</head>
<body>
<h2>Admin Options</h2>
<form action="enter-new.mvc" method="post">
    <input type="submit" value="Push to Add a Question" />
</form>
<form action="all.mvc" method="post">
    <input type="submit" value="View all Questions" />
</form>

<button onclick="testAuth()">Press</button>

<%@include file="playerOptions.jsp" %>

<div id="all"></div>
</body>

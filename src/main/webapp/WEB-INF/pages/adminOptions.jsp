<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<head>
<style type="text/css" media="screen">
.pagecontent {
margin: 50px auto 0;
position: relative;

}

</style>
<%@include file="topbar.jsp" %>
<script>
function getToken(){
	if(gauth != null) {
		googleUser = gauth.currentUser.get(); 
		let id_token = googleUser.getAuthResponse().id_token;
		
		var t1 = document.getElementById("Token1");
		var t2 = document.getElementById("Token2");
		var t3 = document.getElementById("Token3");
		
		t1.value = id_token; 
		t2.value = id_token; 
		t3.value = id_token; 

  	} else {
  		window.location.replace(domain);
  	};
}

</script>
</head>

<body>
<div class='pagecontent'>
<h2>Admin Options</h2>
<form action="enter-new.mvc" method="post" onsubmit='getToken()'>
    <input type="submit" value="Push to Add a Question" />
    <input id='Token1' name='idtoken' value="" type='text' hidden='true'/>
</form>
<form action="edit-old.mvc" method="post" onsubmit='getToken()'>
    <input type="submit" value="Push to Edit a Question" />
    <input id='Token2' name='idtoken' value="" type='text' hidden='true'/>
</form>

<form action="all.mvc" method="post" onsubmit='getToken()'>
    <input type="submit" value="View all Questions" />
    <input id='Token3' name='idtoken' value="" type='text' hidden='true'/>
</form>



<%@include file="playerOptionsContent.jsp" %>

<div id="all"></div>



</div>
</body>
</html>
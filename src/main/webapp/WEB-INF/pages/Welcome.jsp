<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix= "mvc"  %>    
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<meta name="google-signin-client_id" content=${signIn}>
	<script type="text/javascript">
	function onSignIn(googleUser) {
		var profile = googleUser.getBasicProfile();
		alert('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  	alert('Name: ' + profile.getName());
		alert('Image URL: ' + profile.getImageUrl());
	 	alert('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
		  var profile = googleUser.getBasicProfile();
		  var id_token = googleUser.getAuthResponse().id_token;
		  var postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'processSignIn.mvc');
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
		     	 document.getElementById("reply").innerHTML = this.responseText;
		    	 }
		  }
		  postRequest.send('idtoken=' + id_token);
		  postRequest.close()
		}
	</script>
	
	
	<title>Welcome to the Game!</title>
	
	
</head>

<body>
	<h2>Welcome to the Objection Database!</h2>

<div id="reply">
	Please sign in to continue
	<div class="g-signin2" data-onsuccess="onSignIn"></div>
		<a href="all.mvc"> <button >Push to show Database</button>
	</a>
</div>
</body>
</html>
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
		  var id_token = googleUser.getAuthResponse().id_token;
		  var xhr = new XMLHttpRequest();
		  xhr.open('POST', 'all.mvc');
		  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  xhr.onload = function() {
		    console.log('Signed in as: ' + xhr.responseText);
		  };
		  xhr.send('idtoken=' + id_token);
		}
	</script>
	
	
	<title>Welcome to the Game!</title>
	
	
</head>

<body>
	<h2>Welcome to the Objection Database!</h2>
	<a href="all.mvc">
  	  <button >Push to show Database</button>
	</a>
<div class="g-signin2" data-onsuccess="onSignIn"></div>
</body>
</html>
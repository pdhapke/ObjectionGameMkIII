<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		let id_token = googleUser.getAuthResponse().id_token;
	 	let postForm = document.createElement('form'); 
	 	postForm.action = "processSignIn.mvc"; 
	 	postForm.method = "post"; 
	 	let  tokenField = document.createElement('input');
	 	tokenField.name = 'idtoken';
	 	tokenField.value = id_token; 
	 	postForm.appendChild(tokenField); 
	 	document.getElementById("formDiv").appendChild(postForm);
	 	postForm.submit(); 
	} 
	 	
		 
		 /*
	 	let postRequest = new XMLHttpRequest();
		  postRequest.open('POST', 'processSignIn.mvc');
		  postRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		  postRequest.onreadystatechange = function() {
			     if (this.readyState == 4 && this.status == 200) {
			    	$("reply").html.load(this.response);
			    	 //document.getElementById("reply").innerHTML=;
			    	// document.getElementById("reply").innerHTML= this.response;
		    	 }
		  }
		  postRequest.send('idtoken=' + id_token);
		  console.log('sending...');
		}
	*/
	</script>
	
	
	<title>Welcome to the Game!</title>
	
	
</head>

<body>
	<h2>Welcome to the Objection Database!</h2>

<div id="reply">
	<span>Please sign in to continue</span>
	<div class="g-signin2" data-onsuccess="onSignIn"></div>
		
</div>
<div id="formDiv" hidden="true"></div>
</body>
</html>
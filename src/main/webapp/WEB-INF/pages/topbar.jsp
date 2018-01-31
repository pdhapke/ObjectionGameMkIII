<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 
	<script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
	<meta name="google-signin-client_id" content="78078129062-5hal2mpuftppvnfrbehdjttnnelhqu02.apps.googleusercontent.com">
	<script type="text/javascript">
	var gamelocation = "Game.mvc"
	var gauth = null;
	
	function onpageload (){
		gapi.load('client:auth2', onGoogleDAO);
	}
	function onGoogleDAO(){
		var googleConfig = {
				client_id: '78078129062-5hal2mpuftppvnfrbehdjttnnelhqu02.apps.googleusercontent.com'
				}
		gapi.auth2.init(googleConfig).then(onGoogleReady());
	}
	function onGoogleReady(){
		//get logged in status
		gauth = gapi.auth2.getAuthInstance();
		updatepage()
	 	}
	function updatepage(){	
		if (gauth == null){
			onpageload(); 
		}else{
			
		var loggedin = gauth.isSignedIn.get();
		
		//get both objects to be hidden
		var signin = document.getElementById("signin");
		var signout = document.getElementById("signout");
		//hide the one that is not needed, show that one that is
		
		if (loggedin){
			signin.style.display = 'none';
			signout.style.display = 'block';
			
			
		} else {
			signout.style.display = 'none';
			signin.style.display = 'block';
		}
		}
		
	}
	function launchGame(){
	
		if(gauth == null || !gauth.isSignedIn.get()){
			alert("Please sign in")
			onpageload(); 
		} else {
		
		googleUser = gauth.currentUser.get(); 
		let id_token = googleUser.getAuthResponse().id_token;
	 	let postForm = document.createElement('form'); 
	 	postForm.action = gamelocation; //
	 	postForm.method = "post"; 
	 	let  tokenField = document.createElement('input');
	 	tokenField.name = 'idtoken';
	 	tokenField.value = id_token; 
	 	postForm.appendChild(tokenField); 
	 	document.getElementById("formDiv").appendChild(postForm);
	 	postForm.submit(); 
		}
		return false; 
	}
	
	function onSignIn(googleUser) {
		onpageload(); 
	} 
	function signOut() {
	    if(gauth != null) {
	    	 gauth.signOut().then(function () {
	   	     console.log('User signed out.');
	   	     updatepage(); 
	   	    });
	    }
	  }
	function renderButton() {
	      gapi.signin2.render('signin', {
	        'scope': 'profile email',
	        'width': 230,
	        'height': 50,
	        'longtitle': true,
	        'theme': 'dark',
	        'onsuccess': onSignInStub,
	        'onfailure': onFailure
	      });
	      onpageload(); 
	    }
	function onFailure(error) {
	      console.log(error);
	    }
	function onSignInStub () {
		  onpageload(); 
	}
	</script>


<style type="text/css" media="screen">
.pagecontent {
margin: 50px auto 0;
position: relative;

}
.buttonbar {
    background-color: #b37700;
    overflow: hidden;
    position: fixed;
    height: 50px;
    top: 0;
    left: 0; 
    width: 100%;
}
.signout {
	float: left;
    display: block;
    text-align: center;
    height: 50px;
    width: 230px;
    padding: 15px 5px;
    text-decoration: none;
}
.signoutlink{
	display: block;
	width: 100px; 
}
.game{
	display: block;
	width: 130px; 
}
.buttonbar div {
	float: left;
    display: block;
    text-align: center;
    padding: 0px 0px;
    text-decoration: none;
}
.buttonbar a {
	display: inline-block;
    float: left;
    color: #ffffff;
    text-align: center;
    padding: 15px 10px;
    text-decoration: none;
    vertical-align: middle;
}
.buttonbar.signin{
 	float: left;
    display: block;
    padding: 0px 0px;  
}
.buttonbar a:hover {
  background-color: #ffaa00;
  color: white;
}

</style>

</head>
<body>
<div class='buttonbar' id='buttonbar' onload="onpageload()">
	<div id='signout' class='signout'>
		<a id='signoutlink' class='signoutllink' href=# onclick="signOut();">Sign out</a>
		<a id='game' class='game' onclick="launchGame()" href=#>Objection Game</a>
	</div>
	<div id='signin'></div>
<a id='home' class='home' href="">Home</a>

<a id='ObjectionReference' class='ObjectionReference' href="">Objection Rules and Reference</a>
<a id='basics' class='basics' href="" >Mock Trial Basics</a>
<a id='Guide' class='Guide' href="" >Guide</a>
<a id='links' class='links' href="" >Links</a>
<a id='about' class='about' href="" >About</a>
</div>

<div class='pagecontent'>




</div>


</body>
<footer>
<div id="formDiv" hidden="true"></div>
<%@include file="../../../topbar-js.html" %>

</footer>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if( request.getSession().getAttribute("userId") != null ){
		response.sendRedirect("home.jsp");
		return;
}
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
		function isFormValid(){
			//alert("hi "+document.loginForm.userId.value);
			if(document.registerForm.userName.value == ""){
				alert("username cannot be blank");
				return;
			}
			else if(document.registerForm.password.value == ""){
				alert("password field cannot be blank");
				return;
			}	
			else if(document.registerForm.confirmpassword.value == ""){
				alert("password field cannot be blank");
				return;
			}	
			else if(document.registerForm.password.value != document.registerForm.confirmpassword.value){
				alert("passwords much match");
				return;
			}
			else
				registerForm.submit();
		}
	</script>
<title>Insert title here</title>
</head>
<body>
<form name="registerForm" action="register" method="post"  >
<table>
<tr>
<td>username : </td>
<td><input type="text" name="userName" /></td>
</tr>
<tr>
<td>password : </td>
<td><input type="password" name="password" /></td>
</tr>
<tr>
<tr>
<td>confirm password : </td>
<td><input type="password" name="confirmpassword" /></td>
</tr>
<tr>
<td></td>
<td><input type="button" value="register" onclick="isFormValid()" />
</td>
</tr>
</table>
</form>
Go back to <a href="loginPage" >login page</a>
</body>
</html>
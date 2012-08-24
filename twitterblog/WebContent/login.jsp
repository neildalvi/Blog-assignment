<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	if( request.getSession().getAttribute("userId") != null ){
		response.sendRedirect("home.jsp");
		return;
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		function isFormValid(){
			//alert("hi "+document.loginForm.userId.value);
			if(document.loginForm.userName.value == ""){
				alert("username cannot be blank");
				return;
			}
			else if(document.loginForm.password.value == ""){
				alert("password cannot be blank");
				return;
			}	
			else
				loginForm.submit();
		}
	</script>
<title>TwitterBlog | Login Page</title>
</head>
<body >
<h6><%	if(request.getAttribute("infoMessage") != null ){
			out.println(request.getAttribute("infoMessage"));
			request.setAttribute("infoMessage",null);			
		}
	%>
</h6> 
<form name="loginForm" action="login" method="post"  >
<table>
<tr>
<td>user name : </td>
<td><input type="text" name="userName" /></td>
</tr>
<tr>
<td>password : </td>
<td><input type="password" name="password" /></td>
</tr>
<tr>
<td></td>
<td><input type="button" value="log in" onclick="isFormValid()" />
</td>
</tr>
</table>
</form>
<br>
New here <a href="registerPage" >create new account</a>
</body>
</html>
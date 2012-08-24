<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="com.worksapplications.neil.application.services.MessageService,
    com.worksapplications.neil.framework.URLMappingService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%	if(session.getAttribute("userId") == null){
		request.setAttribute("infoMessage","You must be logged in.");
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(new URLMappingService().getURLValue("/"));
		requestDispatcher.forward(request, response);
	}
	int pageNumber;
	if(request.getParameter("page") == null)
		pageNumber = 1;
	else
		pageNumber = Integer.parseInt(request.getParameter("page"));	
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function isStatusFormValid(){
		if(document.statusForm.message.value.trim() == ""){
			alert("status cannot be blank");
			return;
		}
		else {
			statusForm.submit();
		}
	}
	function isMessageFormValid(){
			var flag = false;
			var n = document.messageForm.messageId.length;
			if(document.messageForm.messageId.value != null){
				if(document.messageForm.messageId.checked)
					messageForm.submit();
				else
					alert("no message selected");
			}
			else{
				for(var i=0; i<n; ++i){
					if(document.messageForm.messageId[i].checked){					
						flag = true;
						break;
					}
				}
				if(flag)
					messageForm.submit();
				else
					alert("no message selected");
			}
			
	}
	function clearStatusInput(){
		document.statusForm.message.value = "";
	}
	function fillInDefaultText(){
		if(document.statusForm.message.value == "")
		document.statusForm.message.value = "what's on your mind?";
	}
</script>
<title>twitterblog | home page</title>
</head>
<body>
Hi <%=session.getAttribute("userName") %>,<form method="post" action="logout">
<div style="position:relative;left:325px;top:-25px; width: 0px"><input type="submit" value="logout" /></div>
</form>
<br>
<form name="statusForm" action="postmessage" method="post">
<input type="text" name="message" size="39" value="what's on your mind?" onclick="clearStatusInput()" onblur="fillInDefaultText()"/>
<input type="button" value="post" onclick="isStatusFormValid()"/>
</form>
<br/>
<br/>
<form name="messageForm" method="post" action="deletemessage" >
<div style="position:relative;left:260px;top:0px; width: 76px"><input type="button" value="delete messages" onclick="isMessageFormValid()"/></div>
<table>
 <col width="50px" />
 <col width="200px" />
 <col width="100px" />
 <col width="5px" />
<%  boolean toggle = true;
	String[][] messages =  new MessageService().getRecentMessages(pageNumber);
	if(messages == null || messages[0][0] == null)
		out.println("No messages to be displayed");
	else
	for(int i=0; i< messages.length; ++i){
		if(messages[i][0] == null ) break;
		%>
		<tr style="background:
			<%	if(toggle){
					out.println("#E8E8E8");
				}
				else{
					out.println("#F8F8F8");
				}
				toggle = !toggle;
			%>"
		>
		<td><a href="user/<%=messages[i][1] %>" ><%=messages[i][2] %></a> </td>
		<td><%=messages[i][3] %></td>
		<td><%=messages[i][4] %></td>
		<td>
		<% 	if(messages[i][1].equals((String)session.getAttribute("userId")))
				out.println("<input type=\"checkbox\" name=\"messageId\" value=\""+messages[i][0]+"\" />");
		%>
		</td>
		</tr>
		<%
	}
%>
</table>
</form>

<a href="home.jsp?page=<% if(pageNumber > 1)out.println(pageNumber-1);else out.println(pageNumber);%>">previous</a>
<a href="home.jsp?page=<%=pageNumber+1%>">next</a>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="
    com.worksapplications.neil.application.services.MessageService,com.worksapplications.neil.application.services.LoginService,
    com.worksapplications.neil.framework.URLMappingService"
%>
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
<title>Insert title here</title>
</head>
<body>

This is the profile page of <%=new LoginService().getUserName((String)request.getAttribute("requestedPageOfUserId"))%>

<form name="messageForm" method="post" action="deletemessage" >

<table>
 <col width="300px" />
 <col width="100px" />
 
<%  boolean toggle = true;
	String[][] messages =  new MessageService().getUserMessages((String)request.getAttribute("requestedPageOfUserId"),pageNumber);
	if(messages == null || messages[0][0] == null)
		out.println("No messages to be displayed");
	else
	for(int i=0; i< messages.length; ++i){		
		if(messages[i][0] == null ) break;
%>
		<tr style="background:
				<%	if(toggle)
						{ out.println("#E8E8E8");}
					else
						{out.println("#F8F8F8");}
					toggle = !toggle;
				%>" 
		>		
		<td><%=messages[i][1] %></td>
		<td><%=messages[i][2] %></td>
		
		</tr>
		<%
	}
%>
</table>
</form>

<a href="/twitterblog/user/<%=request.getAttribute("requestedPageOfUserId") %>?page=<% if(pageNumber > 1)out.println(pageNumber-1);else out.println(pageNumber);%>">previous</a>
<a href="/twitterblog/user/<%=request.getAttribute("requestedPageOfUserId") %>?page=<%=pageNumber+1%>">next</a>

<br />

<a href="/twitterblog/home">Go back to home page</a>
</body>
</html>
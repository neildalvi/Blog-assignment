package com.worksapplications.neil.framework;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.worksapplications.neil.framework.URLMappingService;

/**
 * Servlet implementation class URLMappaingServlet
 */
@WebServlet("/")
public class URLMappaingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see Servlet#init()
	 */	
	public void init() throws ServletException{
		
		HashMap<String,String> map = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("/login","/LoginServlet");
				put("/register","/RegisterServlet");
				put("/success","com/worksapplications/neil/application/login/success.jsp");
				put("/","/login.jsp");
				put("/home","/home.jsp");
				put("/postmessage","/MessageServlet");
				put("/deletemessage","/MessageServlet");	
				put("/user","/user.jsp");
				put("/registerPage","/register.jsp");
				put("/loginPage","/register.jsp");
				//put("/css/tablestyle_messages.css","/css/tablestyle_messages.css");
			}
		};
		
		
		new URLMappingService().setMap(map); 

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request,response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String requestedURI = request.getRequestURI();
		
		String requestedServlet;
		int start = requestedURI.indexOf('/', 1);
		int end   = requestedURI.lastIndexOf('/');
		if(start>=end)
			requestedServlet = requestedURI.substring( start  );
		else {
			requestedServlet = requestedURI.substring( start, end);
			request.setAttribute("requestedPageOfUserId",requestedURI.substring(end+1));
			
		}	
		request.setAttribute("requestedServlet", requestedServlet);
		
		/* redirecting user to home.jsp if request for loginPage or registerPage and session is valid*/
		if((requestedServlet.equals("/") || requestedServlet.equals("/loginPage") || requestedServlet.equals("/registerPage")) && request.getSession().getAttribute("userId") != null){
			response.sendRedirect("home.jsp");
			return;
		}
		
		/* redirecting to the login.jsp if the session is invalid except for /login /registerPage*/
		else if(!requestedServlet.equals("/login") && !requestedServlet.equals("/registerPage") && !requestedServlet.equals("/register") && request.getSession().getAttribute("userId") == null){
			
			if(!requestedServlet.equals("/") && !requestedServlet.equals("/loginPage"))
				request.setAttribute("infoMessage","You must be logged in.");
			
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(new URLMappingService().getURLValue("/"));
			requestDispatcher.forward(request, response);
		}
		
		/* forwarding to /login only if userName and password are not null */
		else if(requestedServlet.equals("/login") && (request.getParameter("userName") ==null || request.getParameter("password") == null)){
			
			request.setAttribute("infoMessage","You must be logged in.");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(new URLMappingService().getURLValue("/"));
			requestDispatcher.forward(request, response);
		}
		
		/* handling logout process. */
		else if(requestedServlet.equals("/logout")){
			
			response.setHeader("Cache-Control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			request.getSession().invalidate();
			response.sendRedirect("login.jsp");
		}
		
		/* handling request based on URLMapping */
		else{
			
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(new URLMappingService().getURLValue(requestedServlet));
			requestDispatcher.forward(request, response);
		}
	}

}

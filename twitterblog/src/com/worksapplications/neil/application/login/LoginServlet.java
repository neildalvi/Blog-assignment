package com.worksapplications.neil.application.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.worksapplications.neil.application.DerbyDatabaseService;
import com.worksapplications.neil.application.services.LoginService;
import com.worksapplications.neil.framework.URLMappingService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LoginService loginSerivceInstance;
	
	public void init() throws ServletException{
		
		loginSerivceInstance = new LoginService();
		
	}
         
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * Handles the login process.
	 * 
	 * Required fields : userName and password
	 * 
	 * Forwards to home.jsp if the session is active. ( checks userId in session attribute.)
	 * Forwards to home.jsp if the userName and password are correct.
	 * Forwards to login.jsp if the userName or password are incorrect.  
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int result = loginSerivceInstance.isUserExist(request.getParameter("userName"), request.getParameter("password"));
		if(result == -1){	//invalid user
			System.out.println("invalid user");
			request.setAttribute("infoMessage","Incorrect username or password.");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(new URLMappingService().getURLValue("/"));
			requestDispatcher.forward(request, response);
			return;
		}
		else{	//valid user 
			System.out.println("valid user");
			System.out.println("user id : "+result);
			HttpSession session = request.getSession();
			session.setAttribute("userId",Integer.toString(result));
			session.setAttribute("userName", request.getParameter("userName"));	
			response.sendRedirect("home.jsp");
			return;
		}
		
	}

}

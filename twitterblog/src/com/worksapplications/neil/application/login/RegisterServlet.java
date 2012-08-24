package com.worksapplications.neil.application.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.worksapplications.neil.application.services.RegisterService;
import com.worksapplications.neil.framework.URLMappingService;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RegisterService registerServiceInstance;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {    	
    	registerServiceInstance = new RegisterService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * calls the register service after checking some contraints
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(request.getSession().getAttribute("userId") != null){
			response.sendRedirect("home.jsp");
			return;
		}
		if(registerServiceInstance.setNewUser(request.getParameter("userName"),request.getParameter("password"))){
			request.setAttribute("infoMessage"," You have been registered. Please login. ");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(new URLMappingService().getURLValue("/"));
			requestDispatcher.forward(request, response);
			//response.sendRedirect("login.jsp");
		}
		else{
			
		}
		
	}

}

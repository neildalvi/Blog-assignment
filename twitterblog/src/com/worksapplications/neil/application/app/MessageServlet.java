package com.worksapplications.neil.application.app;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.worksapplications.neil.application.services.MessageService;
import com.worksapplications.neil.framework.URLMappingService;

/**
 * Servlet implementation class MessaageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private MessageService messageServiceInstance;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {    	
        super();
        messageServiceInstance = new MessageService();
        // TODO Auto-generated constructor stub
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
		String userId = (String)request.getSession().getAttribute("userId");
		String message= request.getParameter("message");
		
		String requestedURI = (String)request.getAttribute("requestedServlet");
		/* calling post operation or delete operation depending on the requested String */
		if(requestedURI.indexOf("post") != -1 && messageServiceInstance.putUserMessage(userId,(String)request.getSession().getAttribute("userName"), message)){
			response.sendRedirect("home.jsp");		
		}
		else if(requestedURI.indexOf("delete") != -1){
			
			String messageIds[] = request.getParameterValues("messageId");
			String messagesIdsToBeDeleted = messageIds[0]; 
			if(messageIds != null){
				for(int i=1; i<messageIds.length; ++i)
					messagesIdsToBeDeleted+=","+messageIds[i];
			}
			messageServiceInstance.deleteUserMessage(userId,messageIds);
			
			response.sendRedirect("home.jsp");	
		}
		else {
			
		}
		
	}

}

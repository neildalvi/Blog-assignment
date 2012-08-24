package com.worksapplications.neil.application.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.worksapplications.neil.framework.DatabaseService;

public class MessageService {
	
	private Connection connection;
	private final int limitOfMessagesPerPage = 10;
	
	/*
	 * argument : pageNumber
	 * returns : limitOfMessagesPerPage Messages for that page number
	 * 
	 * Working using the pageNumber, offset is calculated which is used in the query	 
	 */
	public String[][] getRecentMessages(int pageNumber){
		
		//int pageNumber = request.getargument("page");
		String[][] messages;
		
		//SELECT * FROM messagetable ORDER BY datetimeofpost desc fetch next 2 rows only;
		try {
			Class.forName(DatabaseService.DERBY_DRIVER_PATH).newInstance();
			connection = DriverManager.getConnection(DatabaseService.DERBY_DATABASENAME_AND_AUTHENTICATION);
			
			if(connection != null){
				
			int offset = (pageNumber-1)*limitOfMessagesPerPage; /*calculating the offset*/
			PreparedStatement ps = connection.prepareStatement("Select "+ DatabaseService.MESSAGE_IDENTITY_LABEL + "," +DatabaseService.MESSAGE_USER_IDENTITY_LABEL +"," + DatabaseService.MESSAGE_USER_NAME_LABEL + "," + DatabaseService.MESSAGE_CONTENT_LABEL +","+ DatabaseService.MESSAGE_TIMESTAMP_LABEL + " from "+DatabaseService.MESSAGE_TABLE_NAME +" order by "+ DatabaseService.MESSAGE_TIMESTAMP_LABEL +" desc offset "+ offset +" rows fetch next "+ limitOfMessagesPerPage +" rows only");								
			ResultSet result = ps.executeQuery();
			
			messages = new String[limitOfMessagesPerPage][5];
			
			int index=0;
			while(result.next() ){
				messages[index][0] = result.getString(1);
				messages[index][1] = result.getString(2);
				messages[index][2] = result.getString(3);
				messages[index][3] = result.getString(4);
				String[] timeOfPost = result.getString(5).split(" ");
				String date = timeOfPost[0];
				String time = timeOfPost[1];
				String simplifiedTime = time.substring(0,time.lastIndexOf(':'));
				String simplifiedDate = date.substring(date.indexOf('-')+1);
				messages[index++][4] = simplifiedTime+" "+simplifiedDate;
			}
				return messages;
			}
			else
				System.out.println("connection is null");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	/*
	 * arguments : userId, pageNumber
	 * 
	 * returns : limitOfMessagesPerPage Messages for that page number for the used specified by the userId
	 */
	public String[][] getUserMessages(String userId, int pageNumber){
String[][] messages;
		
		//SELECT * FROM messagetable ORDER BY datetimeofpost desc fetch next 2 rows only;
		try {
			Class.forName(DatabaseService.DERBY_DRIVER_PATH).newInstance();
			connection = DriverManager.getConnection(DatabaseService.DERBY_DATABASENAME_AND_AUTHENTICATION);
			
			if(connection != null){
			int offset = (pageNumber-1)*limitOfMessagesPerPage;	
			PreparedStatement ps = connection.prepareStatement("Select "+ DatabaseService.MESSAGE_IDENTITY_LABEL + ","  + DatabaseService.MESSAGE_CONTENT_LABEL +","+ DatabaseService.MESSAGE_TIMESTAMP_LABEL + " from "+DatabaseService.MESSAGE_TABLE_NAME + userId +" order by "+ DatabaseService.MESSAGE_TIMESTAMP_LABEL +" desc offset "+ offset +" rows fetch next "+ limitOfMessagesPerPage +" rows only");								
			ResultSet result = ps.executeQuery();			
			messages = new String[limitOfMessagesPerPage][3];
			
			int index=0;
			while(result.next()){
				messages[index][0] = result.getString(1);
				messages[index][1] = result.getString(2);
				String[] timeOfPost = result.getString(3).split(" ");
				String date = timeOfPost[0];
				String time = timeOfPost[1];
				String simplifiedTime = time.substring(0,time.lastIndexOf(':'));
				String simplifiedDate = date.substring(date.indexOf('-')+1);
				messages[index++][2] = simplifiedTime+" "+simplifiedDate;							
			}
				return messages;
			}
			else
				System.out.println("connection is null");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	/*
	 * argument : userId, userName, message
	 * 
	 * returns : true if the message was inserted in the tables else false.
	 */
	public boolean putUserMessage(String userId,String userName, String message){
		String[][] messages;
		
		//SELECT * FROM messagetable ORDER BY datetimeofpost desc fetch next 2 rows only;
		try {
			Class.forName(DatabaseService.DERBY_DRIVER_PATH).newInstance();
			connection = DriverManager.getConnection(DatabaseService.DERBY_DATABASENAME_AND_AUTHENTICATION);
			
			if(connection != null){
				PreparedStatement messageInsertionInMessageTableQuery = connection.prepareStatement("Insert into " + DatabaseService.MESSAGE_TABLE_NAME +"(" + DatabaseService.MESSAGE_USER_IDENTITY_LABEL +"," + DatabaseService.MESSAGE_USER_NAME_LABEL + "," + DatabaseService.MESSAGE_CONTENT_LABEL + "," + DatabaseService.MESSAGE_TIMESTAMP_LABEL + ") values (?,?,?,current_timestamp)");
				messageInsertionInMessageTableQuery.setString(1,userId);
				messageInsertionInMessageTableQuery.setString(2,userName);
				messageInsertionInMessageTableQuery.setString(3,message);
				messageInsertionInMessageTableQuery.executeUpdate();
				
				PreparedStatement messageIdSelectionForLatestMessage = connection.prepareStatement("Select " + DatabaseService.MESSAGE_IDENTITY_LABEL + " from " + DatabaseService.MESSAGE_TABLE_NAME + " order by " + DatabaseService.MESSAGE_TIMESTAMP_LABEL + " desc fetch first row only ");
				ResultSet rs = messageIdSelectionForLatestMessage.executeQuery();
				String messageId= "";
				if(rs != null && rs.next()){
					 messageId = rs.getString(DatabaseService.MESSAGE_IDENTITY_LABEL);
				}
				
				PreparedStatement messageInsertionInUserMessageTableQuery = connection.prepareStatement("Insert into " + DatabaseService.MESSAGE_TABLE_NAME+ userId +"(" + DatabaseService.MESSAGE_USER_IDENTITY_LABEL + ","+DatabaseService.MESSAGE_IDENTITY_LABEL+"," + DatabaseService.MESSAGE_CONTENT_LABEL + "," + DatabaseService.MESSAGE_TIMESTAMP_LABEL + ") values (?,?,?, current_timestamp)");
				messageInsertionInUserMessageTableQuery.setString(1, userId);
				messageInsertionInUserMessageTableQuery.setString(2, messageId);
				messageInsertionInUserMessageTableQuery.setString(3, message);				
				messageInsertionInUserMessageTableQuery.executeUpdate();
				
				connection.commit();	
				return true;
			}
			else
				System.out.println("connection is null");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	
	/*
	 * argument : userId, messageIds (array)
	 * returns : true is the message were deleted.
	 */
	public boolean deleteUserMessage(String userId,String messageIds[]){
		String[][] messages;
		
		//SELECT * FROM messagetable ORDER BY datetimeofpost desc fetch next 2 rows only;
		try {
			Class.forName(DatabaseService.DERBY_DRIVER_PATH).newInstance();
			connection = DriverManager.getConnection( DatabaseService.DERBY_DATABASENAME_AND_AUTHENTICATION );
			
			if(connection != null){
				for(int i=0;i<messageIds.length;i++){
				PreparedStatement ps1 = connection.prepareStatement("Delete from " + DatabaseService.MESSAGE_TABLE_NAME + " where messageid = ?");
				ps1.setString(1,messageIds[i]);
				PreparedStatement ps2 = connection.prepareStatement("Delete from " + DatabaseService.MESSAGE_TABLE_NAME + userId + " where messageid = ?");
				ps2.setString(1,messageIds[i]);							
				ps1.executeUpdate();
				ps2.executeUpdate();
				}
				connection.commit();
				return true;					
			}
			else
				System.out.println("connection is null");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}

}

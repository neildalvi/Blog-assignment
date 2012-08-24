package com.worksapplications.neil.application.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.worksapplications.neil.framework.DatabaseService;

public class RegisterService {

	private Connection connection;
	
	/*
	 * new user is inserted into the database.
	 * 
	 * arguments : userName, password
	 * return true is the new user was created.
	 */	
	public boolean setNewUser(String userName, String password){
		try {
			Class.forName(DatabaseService.DERBY_DRIVER_PATH).newInstance();
			connection = DriverManager.getConnection(DatabaseService.DERBY_DATABASENAME_AND_AUTHENTICATION);
			if(connection != null){
			PreparedStatement newUserInsertionQuery = connection.prepareStatement("Insert into "+ DatabaseService.LOGIN_TABLE_NAME +"("+DatabaseService.LOGIN_USER_NAME_LABEL+","+DatabaseService.LOGIN_PASSWORD_LABEL+") values (?,?)");
			System.out.println(userName+" "+password);
			newUserInsertionQuery.setString(1,userName);
			newUserInsertionQuery.setString(2,password);		
			newUserInsertionQuery.executeUpdate();	
			
			String userId="";
			PreparedStatement userIdSelectionForLatestAddUser = connection.prepareStatement("Select " + DatabaseService.LOGIN_USER_IDENTITY_LABEL + " from " + DatabaseService.LOGIN_TABLE_NAME + " order by " + DatabaseService.LOGIN_USER_IDENTITY_LABEL + " desc fetch first row only ");			
			ResultSet rs = userIdSelectionForLatestAddUser.executeQuery();//.getString(DatabaseService.LOGIN_USER_IDENTITY_LABEL);
			if(rs != null && rs.next()){
				userId = rs.getString(DatabaseService.LOGIN_USER_IDENTITY_LABEL);
			}			
			System.out.println("userid : "+userId);
			PreparedStatement newUserMessageTableCreationQuery = connection.prepareStatement("create table "+DatabaseService.MESSAGE_TABLE_NAME+userId+" ( "+ DatabaseService.MESSAGE_IDENTITY_LABEL +" integer not null, "+ DatabaseService.MESSAGE_USER_IDENTITY_LABEL+" varchar(40) not null, "+ DatabaseService.MESSAGE_CONTENT_LABEL +" varchar(100) not null, "+ DatabaseService.MESSAGE_TIMESTAMP_LABEL +" timestamp, primary key("+DatabaseService.MESSAGE_IDENTITY_LABEL+"))");						
			System.out.println("newUserMessageTable : "+newUserMessageTableCreationQuery.execute());
			connection.commit();
			return true;
			}
			else{
				//System.out.println("connection is null");
				return false;
			}
			
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

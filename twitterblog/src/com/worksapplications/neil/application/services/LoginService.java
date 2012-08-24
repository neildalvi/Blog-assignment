package com.worksapplications.neil.application.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.worksapplications.neil.framework.DatabaseService;

//loading the derby drivers



public class LoginService {
	
	private Connection connection;
	
	public LoginService(){			
	}
	
	/*
	 * parameter : userid
	 * return : userName 
	 * 
	 * accesses the logintable
	 */
	public String getUserName(String userId){
		
		try {
			Class.forName(DatabaseService.DERBY_DRIVER_PATH).newInstance();
			connection = DriverManager.getConnection(DatabaseService.DERBY_DATABASENAME_AND_AUTHENTICATION);
			if(connection != null){			
			PreparedStatement ps = connection.prepareStatement("Select "+ DatabaseService.LOGIN_USER_NAME_LABEL +" from "+ DatabaseService.LOGIN_TABLE_NAME+" where "+ DatabaseService.LOGIN_USER_IDENTITY_LABEL+" = ?");
			ps.setString(1, userId);
			//System.out.println(ps.toString());
									
			ResultSet userName = ps.executeQuery();
			//System.out.println("Query : "+ps.getMaxRows());
			
			while(userName != null && userName.next()){							
					return userName.getString(1);
			}
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
	 * parameters : userId , password
	 * returns -1 if the user doesnot exist
	 * returns userId if the user exits.
	 */
	public int isUserExist(String userId, String password){
		try {
			Class.forName(DatabaseService.DERBY_DRIVER_PATH).newInstance();
			connection = DriverManager.getConnection(DatabaseService.DERBY_DATABASENAME_AND_AUTHENTICATION);
			if(connection != null){
			System.out.println(connection.toString());
			System.out.println("userid : "+userId+" "+password);
			PreparedStatement ps = connection.prepareStatement("Select "+ DatabaseService.LOGIN_USER_IDENTITY_LABEL+","+DatabaseService.LOGIN_PASSWORD_LABEL +" from "+ DatabaseService.LOGIN_TABLE_NAME+" where "+ DatabaseService.LOGIN_USER_NAME_LABEL +" = ?");
			ps.setString(1, userId);
			System.out.println(ps.toString());
									
			ResultSet passwordList = ps.executeQuery();
			System.out.println("Query : "+ps.getMaxRows());
			
			while(passwordList.next()){
				
				if(password.equals(passwordList.getString(2)))
					return Integer.parseInt(passwordList.getString(1));
			}
			}
			else{
				System.out.println("connection is null");
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
		return -1;
	}
	
	

}

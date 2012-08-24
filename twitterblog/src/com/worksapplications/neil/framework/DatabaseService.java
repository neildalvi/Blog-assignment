package com.worksapplications.neil.framework;

public interface DatabaseService {
	
	String DERBY_DRIVER_PATH = "org.apache.derby.jdbc.ClientDriver";
	String DERBY_DATABASENAME_AND_AUTHENTICATION = "jdbc:derby://localhost:1527/twitterblogDB;user=user;password=1234";
	
	//details of login table
	String LOGIN_TABLE_NAME = "LOGINTABLE";
	String LOGIN_USER_IDENTITY_LABEL = "USERID";
	String LOGIN_USER_NAME_LABEL = "USERNAME";
	String LOGIN_PASSWORD_LABEL = "PASSWORD";
	
	//details of message table
	String MESSAGE_TABLE_NAME = "MESSAGETABLE";
	String MESSAGE_USER_IDENTITY_LABEL = "USERID";
	String MESSAGE_USER_NAME_LABEL = "USERNAME";
	String MESSAGE_IDENTITY_LABEL = "MESSAGEID";
	String MESSAGE_CONTENT_LABEL = "MESSAGE";
	String MESSAGE_TIMESTAMP_LABEL = "TIMESTAMPOFPOST";
	
	/* message table of each user has name MESSAGETABLE + userid 
	 * remaining fields are the same.
	 * 
	 * e.g. if userid=3, the corresponding message table would be MESSAGETABLE3
	 */
	
	
	

}

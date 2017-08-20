package com.severson.taskmanager.exceptions;

/**
 * @author andrewseverson
 *
 */
public class UserDoesNotExistException extends Exception{ 
	 
	  /** 
	   *  
	   */ 
	  private static final long serialVersionUID = 1L; 
	   
	  public UserDoesNotExistException(){ 
	    super("The provided user does not exist"); 
	  } 
	   
	  public UserDoesNotExistException(String message){ 
	    super(message); 
	  } 
	   
}
package com.severson.taskmanager.exceptions;

/**
 * @author andrewseverson
 *
 */
public class UserAlreadyAddedToChecklistException extends Exception{ 
	 
	  /** 
	   *  
	   */ 
	  private static final long serialVersionUID = 1L; 
	   
	  public UserAlreadyAddedToChecklistException(){ 
	    super("The provided user is already assigned to this checklist"); 
	  } 
	   
	  public UserAlreadyAddedToChecklistException(String message){ 
	    super(message); 
	  } 
	   
}
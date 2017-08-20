package com.severson.taskmanager.exceptions;

/**
 * @author andrewseverson
 *
 */
public class ChecklistDoesNotExistException extends Exception{ 
	 
	  /** 
	   *  
	   */ 
	  private static final long serialVersionUID = 1L; 
	   
	  /**
	 * 
	 */
	public ChecklistDoesNotExistException(){ 
	    super("The provided checklist does not exist"); 
	  } 
	   
	  public ChecklistDoesNotExistException(String message){ 
	    super(message); 
	  } 
	   
}
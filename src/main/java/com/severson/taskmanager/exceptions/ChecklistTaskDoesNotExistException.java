package com.severson.taskmanager.exceptions;

/**
 * @author andrewseverson
 *
 */
public class ChecklistTaskDoesNotExistException extends Exception{ 
	 
	  /** 
	   *  
	   */ 
	  private static final long serialVersionUID = 1L; 
	   
	  public ChecklistTaskDoesNotExistException(){ 
	    super("The provided checklist task does not exist"); 
	  } 
	   
	  public ChecklistTaskDoesNotExistException(String message){ 
	    super(message); 
	  } 
	   
}
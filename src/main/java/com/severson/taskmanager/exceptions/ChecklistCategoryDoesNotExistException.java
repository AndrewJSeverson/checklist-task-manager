package com.severson.taskmanager.exceptions;

/**
 * @author andrewseverson
 *
 */
public class ChecklistCategoryDoesNotExistException extends Exception{ 
	 
	  /** 
	   *  
	   */ 
	  private static final long serialVersionUID = 1L; 
	   
	  public ChecklistCategoryDoesNotExistException(){ 
	    super("The provided checklist category does not exist"); 
	  } 
	   
	  public ChecklistCategoryDoesNotExistException(String message){ 
	    super(message); 
	  } 
	   
}
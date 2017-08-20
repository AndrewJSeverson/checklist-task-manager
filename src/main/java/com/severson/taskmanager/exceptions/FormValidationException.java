package com.severson.taskmanager.exceptions;

/**
 * @author andrewseverson
 *
 */
public class FormValidationException extends Exception{ 
	 
	  /** 
	   *  
	   */ 
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	   
	  public FormValidationException(){ 
	    super("The provided data is invalid"); 
	  } 
	   
	  public FormValidationException(String message){ 
	    super(message); 
	  } 
	   
}
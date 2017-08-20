package com.severson.taskmanager.responses;

import java.io.Serializable;

/**
 * @author andrewseverson
 *
 */
public class Response implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private String message;

	protected Response() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setResponse(String message) {
		this.message = message;
	}

	public Response(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

}

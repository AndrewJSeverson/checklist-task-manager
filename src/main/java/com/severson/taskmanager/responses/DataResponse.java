package com.severson.taskmanager.responses;

/**
 * @author andrewseverson
 *
 */
public class DataResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object data;

	protected DataResponse() {
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public DataResponse(String status, String response, Object data) {
		super(status, response);
		this.data = data;
	}

}

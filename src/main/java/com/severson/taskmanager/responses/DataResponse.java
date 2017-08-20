package com.severson.taskmanager.responses;

import org.springframework.data.domain.Pageable;

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
	public Pageable pagination;

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

	public DataResponse(String status, String response, Object data, Pageable page) {
		super(status, response);
		this.data = data;
		this.pagination = page;
	}

}

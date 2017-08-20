package com.severson.taskmanager.requests;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author andrewseverson
 *
 */
public class TaskCompletionRequest {

	/**
	 * REQUIRED FIELD
	 * completion date for checklist task
	 */
	@JsonProperty("completionDate")
	private Date completionDate;

	/**
	 * @return the completionDate
	 */
	public Date getCompletionDate() {
		return completionDate;
	}

	/**
	 * @param completionDate the completionDate to set
	 */
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	
	
}

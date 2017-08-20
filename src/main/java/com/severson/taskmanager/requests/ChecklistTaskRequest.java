package com.severson.taskmanager.requests;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author andrewseverson
 *
 */
public class ChecklistTaskRequest {

	/**
	 * REQUIRED FIELD
	 * description for checklist task
	 */
	@JsonProperty("description")
	private String description;

	/**
	 * REQUIRED FIELD
	 * due date for checklist task
	 */
	@JsonProperty("dueDate")
	private Date dueDate;

	/**
	 * REQUIRED FIELD
	 * reminder date for checklist task
	 */
	@JsonProperty("reminderDate")
	private Date reminderDate;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the reminderDate
	 */
	public Date getReminderDate() {
		return reminderDate;
	}

	/**
	 * @param reminderDate the reminderDate to set
	 */
	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}
	
	
}

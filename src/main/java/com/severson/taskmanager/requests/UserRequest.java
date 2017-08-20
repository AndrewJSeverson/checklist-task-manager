package com.severson.taskmanager.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

	/**
	 * REQUIRED FIELD
	 * name for checklist
	 */
	@JsonProperty("firstName")
	private String firstName;
	
	/**
	 * REQUIRED FIELD
	 * name for checklist
	 */
	@JsonProperty("lastName")
	private String lastName;
	
	/**
	 * REQUIRED FIELD
	 * name for checklist
	 */
	@JsonProperty("email")
	private String email;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}

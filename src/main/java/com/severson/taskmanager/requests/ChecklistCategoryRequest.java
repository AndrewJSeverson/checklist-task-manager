package com.severson.taskmanager.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author andrewseverson
 *
 */
public class ChecklistCategoryRequest {
	
	/**
	 * REQUIRED FIELD
	 * name for checklist
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}

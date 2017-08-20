package com.severson.taskmanager.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author andrewseverson
 *
 */
public class ChecklistRequest {

	/**
	 * REQUIRED FIELD
	 * name for checklist
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * OPTIONAL FIELD
	 * Id for the category on the checklist
	 */
	@JsonProperty("checklistCategoryId")
	private Integer checklistCategoryId;

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

	/**
	 * @return the checklistCategoryid
	 */
	public Integer getChecklistCategoryId() {
		return checklistCategoryId;
	}

	/**
	 * @param checklistCategoryid the checklistCategoryid to set
	 */
	public void setChecklistCategoryId(Integer checklistCategoryId) {
		this.checklistCategoryId = checklistCategoryId;
	}
	
}

package com.severson.taskmanager.services;

import com.severson.taskmanager.exceptions.ChecklistCategoryDoesNotExistException;
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.models.ChecklistCategory;
import com.severson.taskmanager.requests.ChecklistCategoryRequest;

/**
 * @author andrewseverson
 *
 */
public interface ChecklistCategoryService {
	
	/**
	 * @return list of checklist categories
	 */
	public Iterable<ChecklistCategory> getAllChecklistCategories();

	/**
	 * @param request
	 * @return new checklist category
	 * @throws FormValidationException
	 */
	public ChecklistCategory addNewChecklistCategory(ChecklistCategoryRequest request) throws FormValidationException;

	/**
	 * @param request
	 * @param checklistCategoryId
	 * @return updated checklist category
	 * @throws ChecklistCategoryDoesNotExistException
	 * @throws FormValidationException
	 */
	public ChecklistCategory updateChecklistCategory(ChecklistCategoryRequest request, Integer checklistCategoryId) throws ChecklistCategoryDoesNotExistException, FormValidationException;
}

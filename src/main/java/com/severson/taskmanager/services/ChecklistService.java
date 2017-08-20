package com.severson.taskmanager.services;

import com.severson.taskmanager.exceptions.ChecklistCategoryDoesNotExistException;
import com.severson.taskmanager.exceptions.ChecklistDoesNotExistException;
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.exceptions.UserAlreadyAddedToChecklistException;
import com.severson.taskmanager.exceptions.UserDoesNotExistException;
import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.requests.ChecklistRequest;

/**
 * @author andrewseverson
 *
 */
public interface ChecklistService {
	
	/**
	 * @return
	 */
	public Iterable<Checklist> getAllChecklists();
	
	/**
	 * @param request
	 * @return new checklist
	 * @throws FormValidationException
	 */
	public Checklist createNewChecklist(ChecklistRequest request) throws FormValidationException, ChecklistCategoryDoesNotExistException;
	
	/**
	 * @param request
	 * @param checklistId
	 * @return updated checklist
	 * @throws FormValidationException
	 * @throws ChecklistDoesNotExistException
	 */
	public Checklist updateChecklist(ChecklistRequest request, Integer checklistId) throws FormValidationException, ChecklistDoesNotExistException, ChecklistCategoryDoesNotExistException;
	
	/**
	 * @param checklistId
	 * @param userId
	 * @return new checklist data
	 * @throws ChecklistDoesNotExistException
	 * @throws UserDoesNotExistException
	 * @throws UserAlreadyAddedToChecklistException
	 */
	public Checklist addUserToChecklist(Integer checklistId, Integer userId) throws ChecklistDoesNotExistException, UserDoesNotExistException, UserAlreadyAddedToChecklistException;
	
	/**
	 * @param checklistId
	 * @param userId
	 * @return new checklist data
	 * @throws ChecklistDoesNotExistException
	 * @throws UserDoesNotExistException
	 */
	public Checklist removeUserFromChecklist(Integer checklistId, Integer userId) throws ChecklistDoesNotExistException, UserDoesNotExistException;

}

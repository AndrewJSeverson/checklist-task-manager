package com.severson.taskmanager.services;

import com.severson.taskmanager.exceptions.ChecklistDoesNotExistException;
import com.severson.taskmanager.exceptions.UserDoesNotExistException;
import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.models.User;
import com.severson.taskmanager.requests.UserRequest;

/**
 * @author andrewseverson
 *
 */
public interface UserService {
	
	/**
	 * @param userId
	 * @return list of checklists for user
	 * @throws UserDoesNotExistException
	 */
	public Iterable<Checklist> getChecklistsForUser(Integer userId) throws UserDoesNotExistException;
	
	/**
	 * @param checklistId
	 * @param userId
	 * @return checklist and data related to user
	 * @throws UserDoesNotExistException
	 * @throws ChecklistDoesNotExistException
	 */
	public Checklist getChecklistForUser(Integer checklistId, Integer userId) throws UserDoesNotExistException, ChecklistDoesNotExistException; 
	
	/**
	 * @param request
	 * @return new user
	 */
	public User addUserToDatabase(UserRequest request);
}

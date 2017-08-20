package com.severson.taskmanager.services;

import com.severson.taskmanager.exceptions.ChecklistDoesNotExistException;
import com.severson.taskmanager.exceptions.ChecklistTaskDoesNotExistException;
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.exceptions.UserDoesNotExistException;
import com.severson.taskmanager.models.ChecklistTask;
import com.severson.taskmanager.models.TaskCompletion;
import com.severson.taskmanager.requests.ChecklistTaskRequest;
import com.severson.taskmanager.requests.TaskCompletionRequest;

/**
 * @author andrewseverson
 *
 */
public interface ChecklistTaskService {

	/**
	 * @param checklistId
	 * @param request
	 * @return new checklist task
	 * @throws FormValidationException
	 * @throws ChecklistDoesNotExistException
	 */
	public ChecklistTask addChecklistTask(Integer checklistId, ChecklistTaskRequest request) throws FormValidationException, ChecklistDoesNotExistException;

	/**
	 * @param checklistTaskId
	 * @param request
	 * @return updated checklist task
	 * @throws FormValidationException
	 * @throws ChecklistTaskDoesNotExistException
	 */
	public ChecklistTask updateChecklistTask(Integer checklistTaskId, ChecklistTaskRequest request) throws FormValidationException, ChecklistTaskDoesNotExistException;

	/**
	 * @param checklistTaskId
	 * @throws ChecklistTaskDoesNotExistException
	 */
	public void removeChecklistTask(Integer checklistTaskId) throws ChecklistTaskDoesNotExistException;
	
	/**
	 * @param userId
	 * @param checklistTaskId
	 * @param request
	 * @return updated task completion
	 * @throws UserDoesNotExistException
	 * @throws ChecklistTaskDoesNotExistException
	 */
	public TaskCompletion updateTaskCompletion(Integer userId, Integer checklistTaskId, TaskCompletionRequest request) throws UserDoesNotExistException, ChecklistTaskDoesNotExistException;
}

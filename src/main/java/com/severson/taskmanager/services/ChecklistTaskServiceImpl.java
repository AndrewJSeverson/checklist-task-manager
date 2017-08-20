package com.severson.taskmanager.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.severson.taskmanager.exceptions.ChecklistDoesNotExistException;
import com.severson.taskmanager.exceptions.ChecklistTaskDoesNotExistException;
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.exceptions.UserDoesNotExistException;
import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.models.ChecklistTask;
import com.severson.taskmanager.models.ChecklistUser;
import com.severson.taskmanager.models.TaskCompletion;
import com.severson.taskmanager.repositories.ChecklistRepository;
import com.severson.taskmanager.repositories.ChecklistTaskRepository;
import com.severson.taskmanager.repositories.ChecklistUserRepository;
import com.severson.taskmanager.repositories.TaskCompletionRepository;
import com.severson.taskmanager.requests.ChecklistTaskRequest;
import com.severson.taskmanager.requests.TaskCompletionRequest;
import com.severson.taskmanager.utilities.FormValidationHelper;

/**
 * @author andrewseverson
 *
 */
@Service(value = "ChecklistTaskService")
public class ChecklistTaskServiceImpl implements ChecklistTaskService{
	
	@Autowired
	ChecklistRepository checklistRepository;
	@Autowired
	ChecklistTaskRepository checklistTaskRepository;
	@Autowired
	ChecklistUserRepository checklistUserRepository;
	@Autowired
	TaskCompletionRepository taskCompletionRepository;
	
	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistTaskService#addChecklistTask(java.lang.Integer, com.severson.taskmanager.requests.ChecklistTaskRequest)
	 */
	@Override
	public ChecklistTask addChecklistTask(Integer checklistId, ChecklistTaskRequest request)
			throws FormValidationException, ChecklistDoesNotExistException {
		// check for checklist in the repository
		Checklist checklist = checklistRepository.findOne(checklistId);
		if(checklist == null){
			throw new ChecklistDoesNotExistException();
		}
				
		/// object to be saved 
		ChecklistTask checklistTaskToCreate = new ChecklistTask();
		// validate fields
		checklistTaskToCreate = new ChecklistTask(
				FormValidationHelper.validateStringField(false, 2, 255, request.getDescription(), "Checklist Task Description"),
				checklist, 
				FormValidationHelper.validateDateFields(false, new Date(), request.getDueDate(), "Checklist Due Date"),
				FormValidationHelper.validateDateFields(false, new Date(), request.getReminderDate(), "Reminder Date")
				);
		
		// save and return
		return checklistTaskRepository.save(checklistTaskToCreate);
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistTaskService#updateChecklistTask(java.lang.Integer, com.severson.taskmanager.requests.ChecklistTaskRequest)
	 */
	@Override
	public ChecklistTask updateChecklistTask(Integer checklistTaskId, ChecklistTaskRequest request)
			throws FormValidationException, ChecklistTaskDoesNotExistException {
		// check for checklist task in the repository
		ChecklistTask checklistTaskToUpdate = checklistTaskRepository.findOne(checklistTaskId);
		if(checklistTaskToUpdate == null){
			throw new ChecklistTaskDoesNotExistException();
		}
		
		// validate fields
		checklistTaskToUpdate.setDescription(FormValidationHelper.validateStringField(false, 2, 255, request.getDescription(), "Checklist Task Description"));
		checklistTaskToUpdate.setDueDate(FormValidationHelper.validateDateFields(false, new Date(), request.getDueDate(), "Checklist Due Date"));
		checklistTaskToUpdate.setReminderDate(FormValidationHelper.validateDateFields(false, new Date(), request.getReminderDate(), "Reminder Date"));
		
		// save and return
		return checklistTaskRepository.save(checklistTaskToUpdate);
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistTaskService#removeChecklistTask(java.lang.Integer)
	 */
	@Override
	public void removeChecklistTask(Integer checklistTaskId) throws ChecklistTaskDoesNotExistException {
		// check for checklist in the repository
		ChecklistTask checklistTaskToUpdate = checklistTaskRepository.findOne(checklistTaskId);
		if(checklistTaskToUpdate == null){
			throw new ChecklistTaskDoesNotExistException();
		}
		
		// remove form repository
		checklistTaskRepository.delete(checklistTaskId);
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistTaskService#updateTaskCompletion(java.lang.Integer, java.lang.Integer, com.severson.taskmanager.requests.TaskCompletionRequest)
	 */
	@Override
	public TaskCompletion updateTaskCompletion(Integer userId, Integer checklistTaskId, TaskCompletionRequest request)
			throws UserDoesNotExistException, ChecklistTaskDoesNotExistException {
		// check for checklistTask in the repository
		ChecklistTask checklistTask = checklistTaskRepository.findOne(checklistTaskId);
		if(checklistTask == null){
			throw new ChecklistTaskDoesNotExistException();
		}
		
		// check for user, or if they exist for the checklist
		ChecklistUser user = checklistUserRepository.findUserForChecklist(checklistTask.getChecklist().getId(), userId);
		if(user == null){
			throw new UserDoesNotExistException();
		}
		
		// check for existing object
		TaskCompletion taskCompletion = taskCompletionRepository.findTaskCompletionForUserAndTask(userId, checklistTaskId);
		if(taskCompletion == null){
			taskCompletion = new TaskCompletion(request.getCompletionDate(), checklistTask, user);
		} else{
			// if object exists we just update the date
			taskCompletion.setCompletionDate(request.getCompletionDate());
		}
		
		// save and return object
		return taskCompletionRepository.save(taskCompletion);
	}

	
}

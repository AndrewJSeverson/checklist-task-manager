package com.severson.taskmanager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.severson.taskmanager.exceptions.ChecklistDoesNotExistException;
import com.severson.taskmanager.exceptions.UserDoesNotExistException;
import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.models.ChecklistTask;
import com.severson.taskmanager.models.TaskCompletion;
import com.severson.taskmanager.models.User;
import com.severson.taskmanager.repositories.ChecklistRepository;
import com.severson.taskmanager.repositories.TaskCompletionRepository;
import com.severson.taskmanager.repositories.UserRepository;

/**
 * @author andrewseverson
 *
 */
@Service(value = "UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	ChecklistRepository checklistRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TaskCompletionRepository taskCompletionRepository;
	
	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.UserService#getChecklistsForUser(java.lang.Integer)
	 */
	@Override
	public Iterable<Checklist> getChecklistsForUser(Integer userId) throws UserDoesNotExistException {
		// check for user
		User userToAdd = userRepository.findOne(userId);
		if(userToAdd == null){
			throw new UserDoesNotExistException();
		}
		
		// grab all checklists for user
		Iterable<Checklist> userChecklists = checklistRepository.findAllForUser(userId);
		
		// determine completeness
		for(Checklist checklist : userChecklists){
			// determine completeness of checklist for user
			checklist = mergeChecklistWithUserTasksAndCompletions(checklist, userId);
		}
		
		return userChecklists;
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.UserService#getChecklistForUser(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Checklist getChecklistForUser(Integer checklistId, Integer userId)
			throws UserDoesNotExistException, ChecklistDoesNotExistException {
		// check for checklist in the repository
		Checklist checklist = checklistRepository.findOne(checklistId);
		if(checklist == null){
			throw new ChecklistDoesNotExistException();
		}
		
		// check for user
		User user = userRepository.findOne(userId);
		if(user == null){
			throw new UserDoesNotExistException();
		}
		
		// make sure the user is assigned to the checklist
		boolean usersAlreadyAdded = checklist.getChecklistUsers().stream()
	            .anyMatch(u -> Integer.valueOf(u.getUser().getId()).equals(Integer.valueOf(userId)));
		if(!usersAlreadyAdded){
			throw new UserDoesNotExistException();
		}
		
		// determine completeness of checklist for user
		return mergeChecklistWithUserTasksAndCompletions(checklist, userId);
	}
	
	/*
	 * Helper Methods
	 */
	
	/**
	 * @param checklist
	 * @param userId
	 * @return checklist with user information
	 */
	private Checklist mergeChecklistWithUserTasksAndCompletions(Checklist checklist, Integer userId){
		// go over each task in the checklist and look for the users completions
		// if not there is no task completion or null completion date then task not done
		boolean allChecklistTasksComplete = true;
		for(ChecklistTask task : checklist.getChecklistTasks()){
			// loop over completions and compare/look for this users
			boolean userCompletedTask = false;
			// we only want to return this users task completions
			List<TaskCompletion> userTaskCompletions = new ArrayList<TaskCompletion>();
			for(TaskCompletion completion : task.getTaskCompletions()){
				if(completion.getChecklistUser().getUser().getId() == userId &&
						completion.getCompletionDate() != null){
					// user has completion for this task
					userTaskCompletions.add(completion);
					task.setTaskComplete(true);
					userCompletedTask = true;
					break;
				}
			}
			// set completions for this task to just the users data
			task.setTaskCompletions(userTaskCompletions);
			if(!userCompletedTask){
				allChecklistTasksComplete = false;
			}
		}
		// did user complete all tasks?
		checklist.setChecklistComplete(allChecklistTasksComplete);
		
		
		// return checklist with new transient content
		return checklist;
	}
}

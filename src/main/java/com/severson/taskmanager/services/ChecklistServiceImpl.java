package com.severson.taskmanager.services;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.severson.taskmanager.exceptions.ChecklistCategoryDoesNotExistException;
import com.severson.taskmanager.exceptions.ChecklistDoesNotExistException;
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.exceptions.UserAlreadyAddedToChecklistException;
import com.severson.taskmanager.exceptions.UserDoesNotExistException;
import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.models.ChecklistCategory;
import com.severson.taskmanager.models.ChecklistUser;
import com.severson.taskmanager.models.User;
import com.severson.taskmanager.repositories.ChecklistCategoryRepository;
import com.severson.taskmanager.repositories.ChecklistRepository;
import com.severson.taskmanager.repositories.ChecklistUserRepository;
import com.severson.taskmanager.repositories.TaskCompletionRepository;
import com.severson.taskmanager.repositories.UserRepository;
import com.severson.taskmanager.requests.ChecklistRequest;
import com.severson.taskmanager.utilities.FormValidationHelper;

/**
 * @author andrewseverson
 *
 */
@Service(value = "ChecklistService")
public class ChecklistServiceImpl implements ChecklistService{
	
	@Autowired
	ChecklistRepository checklistRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ChecklistUserRepository checklistUserRepository;
	@Autowired
	ChecklistCategoryRepository checklistCategoryRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	TaskCompletionRepository taskCompletionRepository;

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistService#createNewChecklist(com.severson.taskmanager.requests.ChecklistRequest)
	 */
	@Override
	public Checklist createNewChecklist(ChecklistRequest request) throws FormValidationException, ChecklistCategoryDoesNotExistException {
		// object to be saved 
		Checklist checklistToCreate = new Checklist();
		
		// if the category is provided then check to make sure it is valid
		ChecklistCategory checklistCategory = null;
		if(request.getChecklistCategoryId() != null){
			checklistCategory = checklistCategoryRepository.findOne(request.getChecklistCategoryId());
			if(checklistCategory == null){
				throw new ChecklistCategoryDoesNotExistException();
			}
		}
				
		// validate fields
		checklistToCreate.setName(FormValidationHelper.validateStringField(false, 2, 255, request.getName(), "Checklist name"));
		checklistToCreate.setChecklistCategory(checklistCategory);
		checklistToCreate.setCreationDate(new Date());
		checklistToCreate.setLastUpdateDate(new Date());
		
		// save and return
		return checklistRepository.save(checklistToCreate);
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistService#updateChecklist(com.severson.taskmanager.requests.ChecklistRequest, java.lang.Integer)
	 */
	@Override
	public Checklist updateChecklist(ChecklistRequest request, Integer checklistId)
			throws FormValidationException, ChecklistDoesNotExistException, ChecklistCategoryDoesNotExistException {
		// check for checklist in the repository
		Checklist checklistToUpdate = checklistRepository.findOne(checklistId);
		if(checklistToUpdate == null){
			throw new ChecklistDoesNotExistException();
		}
		
		// if the category is provided then check to make sure it is valid
		ChecklistCategory checklistCategory = null;
		if(request.getChecklistCategoryId() != null){
			checklistCategory = checklistCategoryRepository.findOne(request.getChecklistCategoryId());
			if(checklistCategory == null){
				throw new ChecklistCategoryDoesNotExistException();
			}
		}
		
		// validate fields
		checklistToUpdate.setName(FormValidationHelper.validateStringField(false, 2, 255, request.getName(), "Checklist name"));
		checklistToUpdate.setChecklistCategory(checklistCategory);
		checklistToUpdate.setLastUpdateDate(new Date());
		
		// save and return
		return checklistRepository.save(checklistToUpdate);
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistService#addUserToChecklist(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Checklist addUserToChecklist(Integer checklistId, Integer userId)
			throws ChecklistDoesNotExistException, UserDoesNotExistException, UserAlreadyAddedToChecklistException {
		// check for checklist in the repository
		Checklist checklistToUpdate = checklistRepository.findOne(checklistId);
		if(checklistToUpdate == null){
			throw new ChecklistDoesNotExistException();
		}
		
		// check for user
		User userToAdd = userRepository.findOne(userId);
		if(userToAdd == null){
			throw new UserDoesNotExistException();
		}
		
		if(checklistToUpdate.getChecklistUsers() != null){
			// make sure the user is not already added
			boolean usersAlreadyAdded = checklistToUpdate.getChecklistUsers().stream()
		            .anyMatch(u -> Integer.valueOf(u.getUser().getId()).equals(Integer.valueOf(userId)));
			if(usersAlreadyAdded){
				throw new UserAlreadyAddedToChecklistException();
			}
		} else {
			checklistToUpdate.setChecklistUsers(new ArrayList<ChecklistUser>());
		}
		// create and save new record
		ChecklistUser newCheckListUser = new ChecklistUser(userToAdd, checklistToUpdate);
		newCheckListUser = checklistUserRepository.save(newCheckListUser);
		
		// send email
		emailService.sendEmail("New Checklist Assignment", 
				"You have been added to the following checklist: " + checklistToUpdate.getName(), 
				userToAdd.getEmail());
		
		// add to checklist and return
		checklistToUpdate.getChecklistUsers().add(newCheckListUser);
		return checklistToUpdate;
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistService#removeUserFromChecklist(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Checklist removeUserFromChecklist(Integer checklistId, Integer userId)
			throws ChecklistDoesNotExistException, UserDoesNotExistException {
		// check for checklist in the repository
		Checklist checklistToUpdate = checklistRepository.findOne(checklistId);
		if(checklistToUpdate == null){
			throw new ChecklistDoesNotExistException();
		}
		
		// check for user
		User userToRemove = userRepository.findOne(userId);
		if(userToRemove == null){
			throw new UserDoesNotExistException();
		}
		
		// delete record for checklist
		checklistUserRepository.removeUserFromChecklist(checklistId, userId);
		
		// return updated checklist
		return checklistRepository.findOne(checklistId);
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistService#getAllChecklists()
	 */
	@Override
	public Iterable<Checklist> getAllChecklists() {
		return checklistRepository.findAll();
	}

}

package com.severson.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.severson.taskmanager.exceptions.ChecklistCategoryDoesNotExistException;
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.models.ChecklistCategory;
import com.severson.taskmanager.repositories.ChecklistCategoryRepository;
import com.severson.taskmanager.requests.ChecklistCategoryRequest;
import com.severson.taskmanager.utilities.FormValidationHelper;

/**
 * @author andrewseverson
 *
 */
@Service(value = "ChecklistCategoryService")
public class ChecklistCategoryServiceImpl implements ChecklistCategoryService{
	
	@Autowired
	ChecklistCategoryRepository checklistCategoryRepository;

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistCategoryService#getAllChecklistCategories()
	 */
	@Override
	public Iterable<ChecklistCategory> getAllChecklistCategories() {
		return getChecklistCategoriesCachable();
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistCategoryService#addNewChecklistCategory(com.severson.taskmanager.requests.ChecklistCategoryRequest)
	 */
	@Override
	public ChecklistCategory addNewChecklistCategory(ChecklistCategoryRequest request) throws FormValidationException {
		// object to be saved 
		ChecklistCategory checklistCategoryToCreate = new ChecklistCategory();
		// validate fields
		checklistCategoryToCreate.setName(FormValidationHelper.validateStringField(false, 2, 100, request.getName(), "Checklist Category"));
				
		// save
		checklistCategoryToCreate = checklistCategoryRepository.save(checklistCategoryToCreate);
		
		// evict cache
		resetChecklistCategoryCache();
		
		// return
		return checklistCategoryToCreate;
	}

	/* (non-Javadoc)
	 * @see com.severson.taskmanager.services.ChecklistCategoryService#updateChecklistCategory(com.severson.taskmanager.requests.ChecklistCategoryRequest, java.lang.Integer)
	 */
	@Override
	public ChecklistCategory updateChecklistCategory(ChecklistCategoryRequest request, Integer checklistCategoryId)
			throws ChecklistCategoryDoesNotExistException, FormValidationException {
		// check for checklist in database
		ChecklistCategory checklistCategoryToUpdate = checklistCategoryRepository.findOne(checklistCategoryId);
		if(checklistCategoryToUpdate == null){
			throw new ChecklistCategoryDoesNotExistException();
		}
		
		// validate fields
		checklistCategoryToUpdate.setName(FormValidationHelper.validateStringField(false, 2, 100, request.getName(), "Checklist Category"));
		
		// save
		checklistCategoryToUpdate = checklistCategoryRepository.save(checklistCategoryToUpdate);
		
		// evict cache
		resetChecklistCategoryCache();
		
		// return
		return checklistCategoryToUpdate;
	}
	
	/*
	 * Helper methods
	 */
	
	/**
	 * @return cached list of categories
	 */
	@Cacheable("getChecklistCategoriesCachable")
	private Iterable<ChecklistCategory> getChecklistCategoriesCachable(){
		return checklistCategoryRepository.findAll(); 
	}
	
	/**
	 * Reset cache
	 */
	@CacheEvict(value = "getChecklistCategoriesCachable", allEntries = true)
	private void resetChecklistCategoryCache(){
		// evict
	}

}

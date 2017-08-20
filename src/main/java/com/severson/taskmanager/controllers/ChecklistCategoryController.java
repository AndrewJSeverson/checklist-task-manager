package com.severson.taskmanager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.severson.taskmanager.exceptions.ChecklistCategoryDoesNotExistException;
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.requests.ChecklistCategoryRequest;
import com.severson.taskmanager.requests.ChecklistRequest;
import com.severson.taskmanager.responses.DataResponse;
import com.severson.taskmanager.responses.Response;
import com.severson.taskmanager.services.ChecklistCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "checklistCategory")
@Component
@RestController
@RequestMapping(value = "/api/v1/checklistCategories")
public class ChecklistCategoryController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ChecklistCategoryService checklistCategoryService;
	
	@ApiOperation(value = "Get all checklist categories ", notes = "")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getChecklistCategories(
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully retrieved checklists categories", checklistCategoryService.getAllChecklistCategories()),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Creates a new checklist category", notes = "Provide the required information to create a new checklist category")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> createChecklistCategory(@RequestBody ChecklistCategoryRequest request,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully created the checklist category", checklistCategoryService.addNewChecklistCategory(request)),
					HttpStatus.OK);
		} catch (FormValidationException e) {
			logger.warn("Bad form input" , e);
			return new ResponseEntity<>(new Response("Error", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Updates an existing checklist category", notes = "Provide the required information to update the checklist category")
	@RequestMapping(value = "/{checklistCategoryId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateChecklist(@RequestBody ChecklistCategoryRequest request, @RequestParam("checklistCategoryId") Integer checklistCategoryId,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully updated the checklist category", checklistCategoryService.updateChecklistCategory(request, checklistCategoryId)),
					HttpStatus.OK);
		} catch (FormValidationException e) {
			logger.warn("Bad form input" , e);
			return new ResponseEntity<>(new Response("Error", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ChecklistCategoryDoesNotExistException e) {
			logger.warn("Bad checklist category id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist category does not exist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

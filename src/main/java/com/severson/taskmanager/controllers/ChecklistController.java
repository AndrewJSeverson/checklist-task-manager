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

import com.severson.taskmanager.exceptions.ChecklistDoesNotExistException;
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.exceptions.UserAlreadyAddedToChecklistException;
import com.severson.taskmanager.exceptions.UserDoesNotExistException;
import com.severson.taskmanager.requests.ChecklistRequest;
import com.severson.taskmanager.requests.ChecklistTaskRequest;
import com.severson.taskmanager.responses.DataResponse;
import com.severson.taskmanager.responses.Response;
import com.severson.taskmanager.services.ChecklistService;
import com.severson.taskmanager.services.ChecklistTaskService;
import com.severson.taskmanager.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author andrewseverson
 *
 */
@Api(value = "checklist")
@Component
@RestController
@RequestMapping(value = "/api/v1/checklists")
public class ChecklistController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ChecklistService checklistService;
	@Autowired
	ChecklistTaskService checklistTaskService;
	@Autowired
	UserService userService;
	
	/*
	 * *************************************************************
	 * CHECKLIST ENDPOINTS
	 * *************************************************************
	 */
	
	/**
	 * @param request
	 * @param token
	 * @return all checklists
	 */
	@ApiOperation(value = "Get all checklists", notes = "")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllChecklists(@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully retrieved checklists", checklistService.getAllChecklists()),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * @param request
	 * @param token
	 * @return newly created checklist
	 */
	@ApiOperation(value = "Creates a new checklist", notes = "Provide the required information to create a new checklist")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> createChecklist(@RequestBody ChecklistRequest request,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully created the checklist.", checklistService.createNewChecklist(request)),
					HttpStatus.OK);
		} catch (FormValidationException e) {
			logger.warn("Bad form input" , e);
			return new ResponseEntity<>(new Response("Error", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * @param request
	 * @param token  ChecklistDoesNotExistException
	 * @return updated checklist
	 */
	@ApiOperation(value = "Updates an existing checklist", notes = "Provide the required information to update the checklist")
	@RequestMapping(value = "/{checklistId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateChecklist(@RequestBody ChecklistRequest request, @RequestParam("checklistId") Integer checklistId,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully updated the checklist.", checklistService.updateChecklist(request, checklistId)),
					HttpStatus.OK);
		} catch (FormValidationException e) {
			logger.warn("Bad form input" , e);
			return new ResponseEntity<>(new Response("Error", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ChecklistDoesNotExistException e) {
			logger.warn("Bad checklist id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist does not exist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * *************************************************************
	 * CHECKLIST USER ENDPOINTS
	 * *************************************************************
	 */
	
	/**
	 * @param checklistId
	 * @param userId
	 * @param token
	 * @return updated checklist
	 */
	@ApiOperation(value = "Add user to checklist", notes = "Provide the user and checklist ids to add user to checklist")
	@RequestMapping(value = "/{checklistId}/users/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addUserToChecklist(@RequestParam("checklistId") Integer checklistId, @RequestParam("userId") Integer userId,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully added the user to the checklist", checklistService.addUserToChecklist(checklistId, userId)),
					HttpStatus.OK);
		} catch (ChecklistDoesNotExistException e) {
			logger.warn("Bad checklist id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist does not exist"), HttpStatus.BAD_REQUEST);
		} catch (UserDoesNotExistException e) {
			logger.warn("Bad user id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided user does not exist"), HttpStatus.BAD_REQUEST);
		} catch (UserAlreadyAddedToChecklistException e) {
			logger.warn("User already added to provided checklist" , e);
			return new ResponseEntity<>(new Response("Error", "The provided user is already added to the checklist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiOperation(value = "Get checklist for user", notes = "Provide the user and checklist ids to get checklist info for user")
	@RequestMapping(value = "/{checklistId}/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getChecklistForUser(@RequestParam("checklistId") Integer checklistId, @RequestParam("userId") Integer userId,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully retrieved checklist for user", userService.getChecklistForUser(checklistId, userId)),
					HttpStatus.OK);
		} catch (ChecklistDoesNotExistException e) {
			logger.warn("Bad checklist id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist does not exist"), HttpStatus.BAD_REQUEST);
		} catch (UserDoesNotExistException e) {
			logger.warn("Bad user id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided user does not exist or the user is not assignmed to the checklist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * @param checklistId
	 * @param userId
	 * @param token
	 * @return updated checklist
	 */
	@ApiOperation(value = "Remove user from checklist", notes = "Provide the user and checklist ids to remove user from checklist")
	@RequestMapping(value = "/{checklistId}/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeUserFromChecklist(@RequestParam("checklistId") Integer checklistId, @RequestParam("userId") Integer userId,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully remove the user from the checklist", checklistService.removeUserFromChecklist(checklistId, userId)),
					HttpStatus.OK);
		} catch (ChecklistDoesNotExistException e) {
			logger.warn("Bad checklist id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist does not exist"), HttpStatus.BAD_REQUEST);
		} catch (UserDoesNotExistException e) {
			logger.warn("Bad user id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided user does not exist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	/*
	 * *************************************************************
	 * CHECKLIST TASK ENDPOINTS
	 * *************************************************************
	 */
	
	/**
	 * @param checklistId
	 * @param request
	 * @param token
	 * @return the checklist task
	 */
	@ApiOperation(value = "Add task to checklist", notes = "Provide the required input fields to create a task form checklist")
	@RequestMapping(value = "/{checklistId}/checklistTasks/", method = RequestMethod.POST)
	public ResponseEntity<?> addTaskToChecklist(@RequestParam("checklistId") Integer checklistId, @RequestBody ChecklistTaskRequest request,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully added the task to the checklist", checklistTaskService.addChecklistTask(checklistId, request)),
					HttpStatus.OK);
		} catch (FormValidationException e) {
			logger.warn("Bad form input" , e);
			return new ResponseEntity<>(new Response("Error", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ChecklistDoesNotExistException e) {
			logger.warn("Bad checklist id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist does not exist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

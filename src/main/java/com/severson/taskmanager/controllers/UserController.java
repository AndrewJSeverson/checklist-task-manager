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

import com.severson.taskmanager.exceptions.ChecklistTaskDoesNotExistException;
import com.severson.taskmanager.exceptions.UserDoesNotExistException;
import com.severson.taskmanager.requests.TaskCompletionRequest;
import com.severson.taskmanager.requests.UserRequest;
import com.severson.taskmanager.responses.DataResponse;
import com.severson.taskmanager.responses.Response;
import com.severson.taskmanager.services.ChecklistTaskService;
import com.severson.taskmanager.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "users")
@Component
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	@Autowired
	ChecklistTaskService checklistTaskService;
	
	/*
	 * *************************************************************
	 * CHECKLIST ENDPOINTS
	 * *************************************************************
	 */
	
	/**
	 * @param userId
	 * @param token
	 * @return checklists and data for user
	 */
	@ApiOperation(value = "Get checklists for a user", notes = "Provide user id to get checklists for that user")
	@RequestMapping(value = "/{userId}/checklists", method = RequestMethod.GET)
	public ResponseEntity<?> getUserChecklists(@RequestParam("userId") Integer userId,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully retrieved checklists for user", userService.getChecklistsForUser(userId)),
					HttpStatus.OK);
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
	 * CHECKLIST TASK COMPLETION ENDPOINTS
	 * *************************************************************
	 */
	
	/**
	 * @param checklistTaskId
	 * @param request
	 * @param userId
	 * @param token
	 * @return the updated task completion
	 */
	@ApiOperation(value = "Update task completion", notes = "Provide the required input fields to update the task completion")
	@RequestMapping(value = "/{userId}/checkListTasks/{checklistTaskId}/taskCompletions", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTaskCompletion(@RequestParam("checklistTaskId") Integer checklistTaskId, @RequestBody TaskCompletionRequest request,
			@RequestParam("userId") Integer userId, @RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully updated the task", checklistTaskService.updateTaskCompletion(userId, checklistTaskId, request)),
					HttpStatus.OK);
		} catch (ChecklistTaskDoesNotExistException e) {
			logger.warn("Bad checklist task id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist task does not exist"), HttpStatus.BAD_REQUEST);
		} catch (UserDoesNotExistException e) {
			logger.warn("Bad user id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided user does not exist or is not assigned to this checklist and task"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	/*
	 * *************************************************************
	 * USER ENDPOINTS _ TEMPORARY UNTIL FULL AUTH INTEGATION FROM UI APP
	 * *************************************************************
	 */
	
	@ApiOperation(value = "Add user to database", notes = "Provide the required input fields to add a user to the database")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody UserRequest request,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully added the user", userService.addUserToDatabase(request)),
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

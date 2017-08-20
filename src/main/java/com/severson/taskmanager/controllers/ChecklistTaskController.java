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
import com.severson.taskmanager.exceptions.FormValidationException;
import com.severson.taskmanager.requests.ChecklistTaskRequest;
import com.severson.taskmanager.responses.DataResponse;
import com.severson.taskmanager.responses.Response;
import com.severson.taskmanager.services.ChecklistTaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author andrewseverson
 *
 */
@Api(value = "checklistTasks")
@Component
@RestController
@RequestMapping(value = "/api/v1/checklistTasks")
public class ChecklistTaskController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ChecklistTaskService checklistTaskService;
	
	/*
	 * *************************************************************
	 * CHECKLIST TASK ENDPOINTS
	 * *************************************************************
	 */
	
	/**
	 * @param checklistTaskId
	 * @param request
	 * @param token
	 * @return the updated checklist
	 */
	@ApiOperation(value = "Update task", notes = "Provide the required input fields to update the task")
	@RequestMapping(value = "/{checklistTaskId}/", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTaskOnChecklist(@RequestParam("checklistTaskId") Integer checklistTaskId, @RequestBody ChecklistTaskRequest request,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			return new ResponseEntity<>(new DataResponse("Success", "Successfully updated the task", checklistTaskService.updateChecklistTask(checklistTaskId, request)),
					HttpStatus.OK);
		} catch (FormValidationException e) {
			logger.warn("Bad form input" , e);
			return new ResponseEntity<>(new Response("Error", e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ChecklistTaskDoesNotExistException e) {
			logger.warn("Bad checklist task id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist task does not exist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * @param checklistTaskId
	 * @param token
	 * @return the updated checklist
	 */
	@ApiOperation(value = "Delete task", notes = "Delete")
	@RequestMapping(value = "/{checklistTaskId}/", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteChecklistTask(@RequestParam("checklistTaskId") Integer checklistTaskId,
			@RequestHeader(value = "authorization", defaultValue = "missing") String token) {
		try{
			checklistTaskService.removeChecklistTask(checklistTaskId);
			return new ResponseEntity<>(new Response("Success", "Successfully deleted the task"),
					HttpStatus.OK);
		} catch (ChecklistTaskDoesNotExistException e) {
			logger.warn("Bad checklist task id" , e);
			return new ResponseEntity<>(new Response("Error", "The provided checklist task does not exist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error processing request", e);
			return new ResponseEntity<>(new Response("Error", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

package com.severson.taskmanager;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.severson.taskmanager.controllers.ChecklistTaskController;
import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.models.ChecklistTask;
import com.severson.taskmanager.models.ChecklistUser;
import com.severson.taskmanager.models.User;
import com.severson.taskmanager.repositories.ChecklistRepository;
import com.severson.taskmanager.repositories.ChecklistTaskRepository;
import com.severson.taskmanager.repositories.ChecklistUserRepository;
import com.severson.taskmanager.repositories.UserRepository;
import com.severson.taskmanager.requests.ChecklistTaskRequest;
import com.severson.taskmanager.requests.TaskCompletionRequest;
import com.severson.taskmanager.requests.UserRequest;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@Rollback(true)
@Transactional
public class ChecklistTaskControllerTests {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ChecklistTaskRepository checklistTaskRepository;
	@Autowired
	ChecklistUserRepository checklistUserRepository;
	@Autowired
	ChecklistRepository checklistRepository;
	@Autowired 
	ChecklistTaskController controller;
	
	/*
	 * Metadata for tests
	 */
	User user = new User("test", "test", "test@test.com");
	UserRequest userRequest = new UserRequest();
	TaskCompletionRequest taskRequest = new TaskCompletionRequest();
	ChecklistTaskRequest checklistTaskRequest = new ChecklistTaskRequest();
	String token = "token";
	Checklist checklist = new Checklist();
	ChecklistUser checklistUser = new ChecklistUser();
	ChecklistTask task = new ChecklistTask();
	
	@Before
	public void setup(){
		// create user
		user = userRepository.save(user);
		userRequest.setEmail("test@test.com");
		userRequest.setFirstName("Test");
		userRequest.setLastName("Test");
		// create checklist
		checklist.setName("Test");
		checklistRepository.save(checklist);
		// add user to checklist
		checklistUser = new ChecklistUser(user, checklist);
		checklistUser = checklistUserRepository.save(checklistUser);
		// save a task
		taskRequest.setCompletionDate(new Date());
		task = new ChecklistTask("Test", checklist, new Date(new Date().getTime() + 1000000), new Date(new Date().getTime() + 1000000));
		task = checklistTaskRepository.save(task);
		checklist = checklistRepository.findOne(checklist.getId());
		checklistTaskRequest.setDescription("test");
		checklistTaskRequest.setDueDate(new Date(new Date().getTime() + 10000));
		checklistTaskRequest.setReminderDate(new Date(new Date().getTime() + 10000));
	}
	
	/*
	 *  UPDATE TASKS COMPLETION TEST
	 */
	
	@Test
	public void validUpdateTask() {
		ResponseEntity<?> result = controller.updateTaskOnChecklist(task.getId(), checklistTaskRequest, token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateTaskBadTaskId() {
		ResponseEntity<?> result = controller.updateTaskOnChecklist(-1, checklistTaskRequest, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateTaskBadForm() {
		checklistTaskRequest.setDueDate(new Date());
		ResponseEntity<?> result = controller.updateTaskOnChecklist(task.getId(), checklistTaskRequest, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());

		checklistTaskRequest.setDueDate(new Date(new Date().getTime() + 10000));
		checklistTaskRequest.setReminderDate(new Date());
		controller.updateTaskOnChecklist(task.getId(), checklistTaskRequest, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
		
		checklistTaskRequest.setReminderDate(new Date(new Date().getTime() + 10000));
		checklistTaskRequest.setReminderDate(null);
		controller.updateTaskOnChecklist(task.getId(), checklistTaskRequest, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	
	}
	
	@Test
	public void invalidUpdateTaskBadUForm() {
		ResponseEntity<?> result = controller.updateTaskOnChecklist(task.getId(), null, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	/*
	 *  UPDATE TASKS COMPLETION TEST
	 */
	
	@Test
	public void validDeleteTask() {
		ResponseEntity<?> result = controller.deleteChecklistTask(task.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidDeleteTaskBadId() {
		ResponseEntity<?> result = controller.deleteChecklistTask(-1, token);
		assertEquals("Assert a valid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	/*
	 * HELPERS
	 */
}

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

import com.severson.taskmanager.controllers.UserController;
import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.models.ChecklistTask;
import com.severson.taskmanager.models.ChecklistUser;
import com.severson.taskmanager.models.User;
import com.severson.taskmanager.repositories.ChecklistRepository;
import com.severson.taskmanager.repositories.ChecklistTaskRepository;
import com.severson.taskmanager.repositories.ChecklistUserRepository;
import com.severson.taskmanager.repositories.UserRepository;
import com.severson.taskmanager.requests.TaskCompletionRequest;
import com.severson.taskmanager.requests.UserRequest;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@Rollback(true)
@Transactional
public class UserControllerTests {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ChecklistTaskRepository checklistTaskRepository;
	@Autowired
	ChecklistUserRepository checklistUserRepository;
	@Autowired
	ChecklistRepository checklistRepository;
	@Autowired 
	UserController controller;
	
	/*
	 * Metadata for tests
	 */
	User user = new User("test", "test", "test@test.com");
	UserRequest userRequest = new UserRequest();
	TaskCompletionRequest taskRequest = new TaskCompletionRequest();
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
	}
	
	/*
	 * ADD USER TO DATABASE
	 */
	
	@Test
	public void validAddUser() {
		ResponseEntity<?> result = controller.addUser(userRequest, token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidAddUser() {
		ResponseEntity<?> result = controller.addUser(null, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	/*
	 * GET CHECKLISTS FOR USER
	 */
	
	@Test
	public void validGetUserChecklists() {
		ResponseEntity<?> result = controller.getUserChecklists(user.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidGetUserChecklsitsBadUser() {
		ResponseEntity<?> result = controller.getUserChecklists(900, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidGetUserChecklsitsBadForm() {
		ResponseEntity<?> result = controller.getUserChecklists(null, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	/*
	 *  UPDATE TASKS COMPLETION TEST
	 */
	
	@Test
	public void validUpdateTaskCompletion() {
		ResponseEntity<?> result = controller.updateTaskCompletion(task.getId(), taskRequest, user.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateTaskCompletionBadTaskId() {
		ResponseEntity<?> result = controller.updateTaskCompletion(-1, taskRequest, user.getId(), token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateTaskCompletionBadUser() {
		ResponseEntity<?> result = controller.updateTaskCompletion(task.getId(), taskRequest, -1, token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateTaskCompletionBadUForm() {
		ResponseEntity<?> result = controller.updateTaskCompletion(task.getId(), null, user.getId(), token);
		assertEquals("Assert a invalid request is submitted", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	
	/*
	 * HELPERS
	 */
	
	
}

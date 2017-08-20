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

import com.severson.taskmanager.controllers.ChecklistController;
import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.models.ChecklistCategory;
import com.severson.taskmanager.models.ChecklistTask;
import com.severson.taskmanager.models.ChecklistUser;
import com.severson.taskmanager.models.User;
import com.severson.taskmanager.repositories.ChecklistCategoryRepository;
import com.severson.taskmanager.repositories.ChecklistRepository;
import com.severson.taskmanager.repositories.ChecklistTaskRepository;
import com.severson.taskmanager.repositories.ChecklistUserRepository;
import com.severson.taskmanager.repositories.UserRepository;
import com.severson.taskmanager.requests.ChecklistRequest;
import com.severson.taskmanager.requests.ChecklistTaskRequest;
import com.severson.taskmanager.requests.TaskCompletionRequest;
import com.severson.taskmanager.requests.UserRequest;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@Rollback(true)
@Transactional
public class ChecklistControllerTests {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ChecklistTaskRepository checklistTaskRepository;
	@Autowired
	ChecklistUserRepository checklistUserRepository;
	@Autowired
	ChecklistRepository checklistRepository;
	@Autowired 
	ChecklistController controller;
	@Autowired
	ChecklistCategoryRepository categoryRepoistory;
	
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
	ChecklistRequest checklistRequest = new ChecklistRequest();
	ChecklistCategory category = new ChecklistCategory();

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
		// create category
		category.setName("test");
		category = categoryRepoistory.save(category);
		checklistRequest.setChecklistCategoryId(category.getId());
		checklistRequest.setName("Test");
	}
	
	
	/*
	 *  GET ALL CHECKLISTS
	 */
	
	@Test
	public void validGetChecklists() {
		ResponseEntity<?> result = controller.getAllChecklists(token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	/*
	 *  CREATE CHECKLISTS
	 */
	
	@Test
	public void validCreateChecklists() {
		ResponseEntity<?> result = controller.createChecklist(checklistRequest, token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
		
		checklistRequest.setChecklistCategoryId(null);
		controller.createChecklist(checklistRequest, token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidCreateChecklistsBadFormData() {
		checklistRequest.setName("");
		ResponseEntity<?> result = controller.createChecklist(checklistRequest, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
		
		checklistRequest.setName(null);
		controller.createChecklist(checklistRequest, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidCreateChecklistsBadCategory() {
		checklistRequest.setChecklistCategoryId(-1);
		ResponseEntity<?> result = controller.createChecklist(checklistRequest, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidCreateChecklistsBadRequest() {
		ResponseEntity<?> result = controller.createChecklist(null, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	/*
	 *  CREATE CHECKLISTS
	 */
	
	@Test
	public void validUpdateChecklists() {
		ResponseEntity<?> result = controller.updateChecklist(checklistRequest, checklist.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
		
		checklistRequest.setChecklistCategoryId(null);
		result = controller.updateChecklist(checklistRequest, checklist.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateChecklistsBadFormData() {
		checklistRequest.setName("");
		ResponseEntity<?> result = controller.updateChecklist(checklistRequest, checklist.getId(), token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
		
		checklistRequest.setName(null);
		result = controller.createChecklist(checklistRequest, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateChecklistsBadChecklistId() {
		ResponseEntity<?> result = controller.updateChecklist(checklistRequest, -1, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateChecklistsBadCategory() {
		checklistRequest.setChecklistCategoryId(-1);
		ResponseEntity<?> result = controller.updateChecklist(checklistRequest, checklist.getId(), token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateChecklistsBadRequest() {
		ResponseEntity<?> result = controller.updateChecklist(null, checklist.getId(), token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	/*
	 *  ADD USER CHECKLIST TESTS
	 */
	
	@Test
	public void validAddUserToChecklist() {
		ResponseEntity<?> result = controller.addUserToChecklist(checklist.getId(), user.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidAddUserToChecklistBadUser() {
		ResponseEntity<?> result = controller.addUserToChecklist(checklist.getId(), -1, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidAddUserToChecklistUserAlreadyAdded() {
		controller.addUserToChecklist(checklist.getId(), user.getId(), token);
		ResponseEntity<?> result = controller.addUserToChecklist(checklist.getId(), user.getId(), token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidAddUserToChecklistBadChecklistId() {
		ResponseEntity<?> result = controller.addUserToChecklist(-1, user.getId(), token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	/*
	 *  REMOVE USER CHECKLIST TESTS
	 */
	
	@Test
	public void validRemoveUserFromChecklist() {
		controller.addUserToChecklist(checklist.getId(), user.getId(), token);
		ResponseEntity<?> result = controller.removeUserFromChecklist(checklist.getId(), user.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidRemoveUserFromChecklistBadChecklistId() {
		ResponseEntity<?> result = controller.removeUserFromChecklist(-1, user.getId(), token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidRemoveUserFromChecklistBadUser() {
		ResponseEntity<?> result = controller.removeUserFromChecklist(checklist.getId(), -1, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());

	}
	
	/*
	 *  GET USER CHECKLIST TESTS
	 */
	
	@Test
	public void validGetUserChecklist() {
		controller.addUserToChecklist(checklist.getId(), user.getId(), token);
		ResponseEntity<?> result = controller.getChecklistForUser(checklist.getId(), user.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidGetUserChecklistBadChecklistId() {
		ResponseEntity<?> result = controller.getChecklistForUser(-1, user.getId(), token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidGetUserChecklistBadUser() {
		ResponseEntity<?> result = controller.getChecklistForUser(checklist.getId(), -1, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	/*
	 *  ADD TASK TO CHECKLIST TESTS
	 */
	
	@Test
	public void validAddTaskToChecklist() {
		ResponseEntity<?> result = controller.addTaskToChecklist(checklist.getId(), checklistTaskRequest, token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidAddTaskToChecklistBadForm() {
		checklistTaskRequest.setDescription("");
		ResponseEntity<?> result = controller.addTaskToChecklist(checklist.getId(), checklistTaskRequest, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
		
		checklistTaskRequest.setDueDate(new Date());
		result = controller.addTaskToChecklist(checklist.getId(), checklistTaskRequest, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());

		checklistTaskRequest.setReminderDate(new Date());
		result = controller.addTaskToChecklist(checklist.getId(), checklistTaskRequest, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidAddTaskToChecklistBadChecklistId() {
		ResponseEntity<?> result = controller.addTaskToChecklist(-1, checklistTaskRequest, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidAddTaskToChecklistBadRequest() {
		ResponseEntity<?> result = controller.addTaskToChecklist(checklist.getId(), null, token);
		assertEquals("Assert an invalid request is submitted", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
}

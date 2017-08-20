package com.severson.taskmanager;

import static org.junit.Assert.assertEquals;

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

import com.severson.taskmanager.controllers.ChecklistCategoryController;
import com.severson.taskmanager.models.ChecklistCategory;
import com.severson.taskmanager.repositories.ChecklistCategoryRepository;
import com.severson.taskmanager.requests.ChecklistCategoryRequest;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@Rollback(true)
@Transactional
public class ChecklistCategoryControllerTests {
	
	@Autowired
	ChecklistCategoryRepository checklistCategoryRepository;
	@Autowired 
	ChecklistCategoryController controller;

	/*
	 * Metadata for tests
	 */
	ChecklistCategory category = new ChecklistCategory();
	ChecklistCategoryRequest request = new ChecklistCategoryRequest();
	String token = "token";
	
	@Before
	public void setup(){
		category = createAndSaveCategory(category);
		request.setName("Test");
	}
	
	/*
	 * GET CATEGORIES ENDPOINT TESTS
	 */
	
	@Test
	public void validGetCategories() {
		ResponseEntity<?> result = controller.getChecklistCategories("");
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	/*
	 * POST CATEGORIES ENDPOINT TESTS
	 */

	@Test
	public void validCreateCategory() {
		ResponseEntity<?> result = controller.createChecklistCategory(request, token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidCreateCategoryBadFormat() {
		request.setName(""); // string not enough characters
		ResponseEntity<?> result = controller.createChecklistCategory(request, token);
		assertEquals("Assert a invalid request is submitted for bad format", HttpStatus.BAD_REQUEST, result.getStatusCode());

		request.setName("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				+ "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				+ "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				+ "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				+ "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"); // string too long
		controller.createChecklistCategory(request, token);
		assertEquals("Assert a invalid request is submitted for bad format long string", HttpStatus.BAD_REQUEST, result.getStatusCode());

		request.setName(null); // string nnull
		controller.createChecklistCategory(request, token);
		assertEquals("Assert a invalid request is submitted for required field missing", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidCreateCategoryBadFormData() {
		ResponseEntity<?> result = controller.createChecklistCategory(null, token);
		assertEquals("Assert a invalid request is submitted for bad form data", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	/*
	 * UPDATE CATEGORIES ENDPOINT TESTS
	 */
	
	@Test
	public void validUpdateCategory() {
		ResponseEntity<?> result = controller.updateChecklist(request, category.getId(), token);
		assertEquals("Assert a valid request is submitted", HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateCategoryBadFormat() {
		request.setName(""); // string not enough characters
		ResponseEntity<?> result = controller.updateChecklist(request, category.getId(), token);
		assertEquals("Assert a invalid request is submitted for bad format", HttpStatus.BAD_REQUEST, result.getStatusCode());

		request.setName("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				+ "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				+ "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				+ "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
				+ "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"); // string too long
		controller.updateChecklist(request, category.getId(), token);
		assertEquals("Assert a invalid request is submitted for bad format long string", HttpStatus.BAD_REQUEST, result.getStatusCode());

		request.setName(null); // string nnull
		controller.updateChecklist(request, category.getId(), token);
		assertEquals("Assert a invalid request is submitted for required field missing", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateCategoryBadCatId() {
		ResponseEntity<?> result = controller.updateChecklist(request, 999999, token); // bad id
		assertEquals("Assert a invalid request is submitted for bad category id", HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	
	@Test
	public void invalidUpdateCategoryBadFormData() {
		ResponseEntity<?> result = controller.updateChecklist(null, null, token);
		assertEquals("Assert a invalid request is submitted for bad form data", HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}
	
	
	/*
	 * HELPERS
	 */
	
	public ChecklistCategory createAndSaveCategory(ChecklistCategory category){
		category.setName("Test");
		return checklistCategoryRepository.save(category);
	}
}

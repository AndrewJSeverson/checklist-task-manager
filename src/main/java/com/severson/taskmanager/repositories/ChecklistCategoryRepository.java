package com.severson.taskmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.severson.taskmanager.models.ChecklistCategory;

public interface ChecklistCategoryRepository extends CrudRepository<ChecklistCategory, Integer> {

}

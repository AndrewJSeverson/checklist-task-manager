package com.severson.taskmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.severson.taskmanager.models.ChecklistTask;

public interface ChecklistTaskRepository extends CrudRepository<ChecklistTask, Integer> {

}

package com.severson.taskmanager.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.severson.taskmanager.models.ChecklistTask;

public interface ChecklistTaskRepository extends CrudRepository<ChecklistTask, Integer> {

	@Query("Select ct from ChecklistTask ct WHERE (ct.reminderDateEmailSent IS NULL OR ct.reminderDateEmailSent = false) AND ct.reminderDate < :dateCheck")
	public List<ChecklistTask> findReminderEmailsToBeSent(@Param("dateCheck") Date dateCheck);
	
	@Query("Select ct from ChecklistTask ct WHERE (ct.dueDateEmailSent IS NULL OR ct.dueDateEmailSent = false) AND ct.dueDate < :dateCheck")
	public List<ChecklistTask> findDueDateEmailsToBeSent(@Param("dateCheck") Date dateCheck);
	
	@Override
	@Modifying
	@Transactional
	@Query("DELETE FROM ChecklistTask ct WHERE ct.id = :taskId")
	public void delete(@Param("taskId") Integer taskId);
}

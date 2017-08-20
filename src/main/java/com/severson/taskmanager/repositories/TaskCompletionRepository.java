package com.severson.taskmanager.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.severson.taskmanager.models.TaskCompletion;

public interface TaskCompletionRepository extends CrudRepository<TaskCompletion, Integer> {

	@Query("Select t from TaskCompletion t where t.checklistUser.user.id = :userId AND t.checklistTask.id = :checklistTaskId")
	public TaskCompletion findTaskCompletionForUserAndTask(@Param("userId") Integer userId, @Param("checklistTaskId") Integer checklistTaskId);

}

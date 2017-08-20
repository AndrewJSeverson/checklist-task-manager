package com.severson.taskmanager.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.severson.taskmanager.models.Checklist;
import com.severson.taskmanager.models.ChecklistUser;

public interface ChecklistUserRepository extends CrudRepository<ChecklistUser, Integer> {

	@Modifying
	@Transactional
	@Query("DELETE FROM ChecklistUser cu WHERE cu.user.id = :userId AND cu.checklist.id = :checklistId")
	public void removeUserFromChecklist(@Param("checklistId") Integer checklistId, @Param("userId") Integer userId);

	@Query("Select cu FROM ChecklistUser cu WHERE cu.user.id = :userId AND cu.checklist.id = :checklistId")
	public ChecklistUser findUserForChecklist(@Param("checklistId") Integer checklistId, @Param("userId") Integer userId);
}

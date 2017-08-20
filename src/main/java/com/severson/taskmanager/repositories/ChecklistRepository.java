package com.severson.taskmanager.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.severson.taskmanager.models.Checklist;

public interface ChecklistRepository extends CrudRepository<Checklist, Integer> {

	
	@Query("Select cl FROM Checklist cl JOIN cl.checklistUsers clu WHERE clu.user.id = :userId")
	public Iterable<Checklist> findAllForUser(@Param("userId") Integer userId);
}

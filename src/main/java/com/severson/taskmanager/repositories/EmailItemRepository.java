package com.severson.taskmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.severson.taskmanager.models.EmailItem;

public interface EmailItemRepository extends CrudRepository<EmailItem, Integer> {

	@Query("Select ei from EmailItem ei WHERE ei.sent = false")
	public List<EmailItem> getUnsentEmailItems();
}

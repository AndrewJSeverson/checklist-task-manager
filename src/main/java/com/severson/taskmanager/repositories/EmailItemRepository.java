package com.severson.taskmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.severson.taskmanager.models.EmailItem;

public interface EmailItemRepository extends CrudRepository<EmailItem, Integer> {

}

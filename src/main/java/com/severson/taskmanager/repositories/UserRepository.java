package com.severson.taskmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.severson.taskmanager.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}

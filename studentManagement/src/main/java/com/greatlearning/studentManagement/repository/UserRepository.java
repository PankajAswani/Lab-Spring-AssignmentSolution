package com.greatlearning.studentManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greatlearning.studentManagement.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUserName(String userName);
}
package com.gourmet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gourmet.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String username);
}

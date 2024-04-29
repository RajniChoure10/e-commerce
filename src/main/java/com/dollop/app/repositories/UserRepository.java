package com.dollop.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByNameContaining(String keyword);

	public boolean existsByName(String name);

	public boolean existsByEmail(String email);

	public boolean existsByPassword(String password);

}

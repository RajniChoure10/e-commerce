package com.dollop.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.entities.Role;

public interface RoleRepository extends JpaRepository<Role,  String> {

}

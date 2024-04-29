package com.dollop.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.app.entities.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

	@Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
	List<Order> findByUserId(@Param("userId") String userId);
}

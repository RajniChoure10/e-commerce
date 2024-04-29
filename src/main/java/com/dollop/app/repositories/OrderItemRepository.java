package com.dollop.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

}

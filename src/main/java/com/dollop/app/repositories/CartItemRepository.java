package com.dollop.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, String>{

}

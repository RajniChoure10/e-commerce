package com.dollop.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dollop.app.entities.Cart;
import com.dollop.app.entities.User;

public interface CartRepository extends JpaRepository<Cart, String> {

	Cart findByUser(User user);

}

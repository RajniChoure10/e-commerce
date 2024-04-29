package com.dollop.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.dtos.CartDto;
import com.dollop.app.services.ICartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ICartService cservice;

	@PostMapping("/cartCreation/{userId}")
	public ResponseEntity<CartDto> cartCreation(@PathVariable String userId) {

		return new ResponseEntity<>(cservice.createCart(userId), HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId) {
		return new ResponseEntity<>(cservice.getCartByUser(userId), HttpStatus.OK);
	}

}

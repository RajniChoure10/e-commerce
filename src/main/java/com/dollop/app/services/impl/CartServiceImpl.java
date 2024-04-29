package com.dollop.app.services.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollop.app.dtos.CartDto;
import com.dollop.app.dtos.UserDto;
import com.dollop.app.entities.Cart;
import com.dollop.app.entities.User;
import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.repositories.CartRepository;
import com.dollop.app.repositories.UserRepository;
import com.dollop.app.services.ICartService;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private UserRepository urepo;

	@Autowired
	private CartRepository crepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CartDto getCartByUser(String userId) {

		User user = urepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user Id is not Found to get cart"));

		Cart cart = crepo.findByUser(user);
		if (cart != null) {
			CartDto cartDto = modelMapper.map(cart, CartDto.class);
			return cartDto;
		}
		else
			throw new ResourceNotFoundException("cart id "+userId+" not found");
	}

	@Override
	public CartDto createCart(String userId) {

		User user = urepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Id is not found to create cart"));

		String cartId = UUID.randomUUID().toString();
		Cart cart = new Cart();
		cart.setCartId(cartId);
		cart.setCreatedAt(new Date());
		cart.setUser(new User(userId));
		Cart cart1 = crepo.save(cart);
		return modelMapper.map(cart1, CartDto.class);
	}
}
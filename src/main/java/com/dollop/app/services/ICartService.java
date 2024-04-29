package com.dollop.app.services;

import com.dollop.app.dtos.CartDto;

public interface ICartService {

	public CartDto createCart(String userId);
	public CartDto getCartByUser(String userId);
	// public CartDto addItemToCart(CartDto cartDto);

	// public String deleteItemFromCart(String cartId, String cartItemId);

	

	// public String clearCart(String cartId);

}

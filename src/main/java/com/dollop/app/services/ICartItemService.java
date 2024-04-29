package com.dollop.app.services;

import com.dollop.app.dtos.CartItemDto;
import com.dollop.app.dtos.PageableResponse;

public interface ICartItemService {

	CartItemDto createCartItem(CartItemDto cartItemDto,String cartId,String productId);
	CartItemDto updateCartItem(CartItemDto cartItemDto,String CartItemId);
	void deleteCategory(String cartItemId);
	CartItemDto getSingleCartItem(String CartItemId);																
	PageableResponse<CartItemDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
	//CartItemDto increaseQuantity(String cartItemId,Integer quantityToAdd);
	
}

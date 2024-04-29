package com.dollop.app.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dollop.app.dtos.CartItemDto;
import com.dollop.app.dtos.PageableResponse;

import com.dollop.app.entities.Cart;
import com.dollop.app.entities.CartItem;
import com.dollop.app.entities.Product;

import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.helper.MyHelper;
import com.dollop.app.repositories.CartItemRepository;
import com.dollop.app.repositories.CartRepository;
import com.dollop.app.repositories.ProductRepository;
import com.dollop.app.services.ICartItemService;

@Service
public class CartItemServiceImpl implements ICartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CartItemDto createCartItem(CartItemDto cartItemDto, String cartId, String productId) {

		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart id is not found to create product"));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Category id is not found to create product"));

		// Check if the cart already contains a cart item with the same product
		Optional<CartItem> optionalCartItem = cart.getItems().stream()
				.filter(item -> item.getProduct().getProductId().equals(productId)).findFirst();

		if (optionalCartItem.isPresent()) {
			// If the cart already contains a cart item with the same product, update its
			// quantity
			CartItem existingCartItem = optionalCartItem.get();
			int newQuantity = existingCartItem.getQuantity() +1;
			existingCartItem.setQuantity(newQuantity);
			return modelMapper.map(existingCartItem, CartItemDto.class);
		} else {
			// If the cart doesn't contain a cart item with the same product, create a new
			// one
			String cartItemId = UUID.randomUUID().toString();
			cartItemDto.setCartItemId(cartItemId);
			cartItemDto.setCart(cart);
			cartItemDto.setProduct(product);
			CartItem newCartItem = modelMapper.map(cartItemDto, CartItem.class);
			cartItemRepository.save(newCartItem);
			return modelMapper.map(newCartItem, CartItemDto.class);
		}
	}

	/*
	 * Cart cart = cartRepository.findById(cartId) .orElseThrow(() -> new
	 * ResourceNotFoundException("Cart id is not found to create product"));
	 * 
	 * Product product = productRepository.findById(productId) .orElseThrow(() ->
	 * new ResourceNotFoundException("Category id is not found to create product"));
	 * 
	 * String cartItemId = UUID.randomUUID().toString();
	 * cartItemDto.setCartItemId(cartItemId); cartItemDto.setCart(cart);
	 * cartItemDto.setProduct(product); //
	 * System.err.println(cartItemDto.getTotalPrice()); CartItem cartItem =
	 * modelMapper.map(cartItemDto, CartItem.class);
	 * cartItemRepository.save(cartItem);
	 * 
	 * CartItemDto newCartItemDto = modelMapper.map(cartItem, CartItemDto.class);
	 * return newCartItemDto;
	 */

//		CartItem cartItem = doToEntity(cartItemDto);
//		CartItem savedCartItem = cartItemRepository.save(cartItem);
//
//		CartItemDto newCartItemDto = entityToDto(savedCartItem);
//		return newCartItemDto;
	// }

	/*
	 * private CartItemDto entityToDto(CartItem savedCartItem) { return
	 * modelMapper.map(savedCartItem, CartItemDto.class); }
	 * 
	 * private CartItem doToEntity(CartItemDto cartItemDto) { return
	 * modelMapper.map(cartItemDto, CartItem.class);
	 */
//------------------------------------------------------	

	public CartItemDto getSingleCartItem(String CartItemId) {
		CartItem cartItem = cartItemRepository.findById(CartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItemId is not found to get cartItem"));
		return modelMapper.map(cartItem, CartItemDto.class);
	}

	@Override
	public CartItemDto updateCartItem(CartItemDto cartItemDto, String CartItemId) {

		CartItem cartItem = cartItemRepository.findById(CartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem is not found to update cartItem"));
		cartItem.setQuantity(cartItemDto.getQuantity());
		cartItem.setTotalPrice(cartItemDto.getTotalPrice());
		cartItem = cartItemRepository.save(cartItem);
		return modelMapper.map(cartItem, CartItemDto.class);
	}

	@Override
	public void deleteCategory(String cartItemId) {
		CartItemDto cartItemDto = getSingleCartItem(cartItemId);
		CartItem cartItem = modelMapper.map(cartItemDto, CartItem.class);
		cartItemRepository.delete(cartItem);
	}

	@Override
	public PageableResponse<CartItemDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<CartItem> page = cartItemRepository.findAll(pageable);
		PageableResponse<CartItemDto> response = MyHelper.getPageableResponse(page, CartItemDto.class);
		return response;
	}


//	public CartItemDto increaseQuantity(String cartItemId, Integer quantityToAdd) {
//		
//		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
//				()->new ResourceNotFoundException("CartItem Id "+ cartItemId+"not exist"));
//		
//		int currentQuantity = cartItem.getQuantity();
//	    cartItem.setQuantity(currentQuantity + quantityToAdd);
//				
//		cartItemRepository.save(null)
	}



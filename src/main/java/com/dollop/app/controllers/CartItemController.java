package com.dollop.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.dtos.CartItemDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.UserDto;
import com.dollop.app.repositories.CartItemRepository;
import com.dollop.app.services.ICartItemService;

@RestController
@RequestMapping("/cartitem")
public class CartItemController {

	@Autowired
	private ICartItemService cartItemService;

	@PostMapping("/{cartId}/{productId}")
	ResponseEntity<CartItemDto> saveCartItem(@RequestBody CartItemDto cartItemDto,@PathVariable String cartId,
			@PathVariable String productId) 
	{
		return new ResponseEntity<CartItemDto>
		(cartItemService.createCartItem(cartItemDto,cartId,productId),HttpStatus.OK);
	}
	
	
	@GetMapping("/{cartItemId}")
	ResponseEntity<CartItemDto> getOneCartItem(@PathVariable String cartItemId)
	{
		return new ResponseEntity<CartItemDto>(cartItemService.getSingleCartItem(cartItemId),HttpStatus.OK);
	}
	
	@PutMapping("/{cartItemId}")
	ResponseEntity<String> editCartItem
	(@RequestBody CartItemDto cartItemDto,
			@PathVariable String cartItemId) {
		
		CartItemDto cartItemDto1 = cartItemService.updateCartItem(cartItemDto, cartItemId);
		String message = "CartItem "+ cartItemId+"edited successfully";
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@DeleteMapping("/{cartItemId}")
   ResponseEntity<String> removeCartItem(@PathVariable String cartItemId)
   {
	   cartItemService.deleteCategory(cartItemId);
	   String msg = "cartItem deleted succuessfully with this id"+ cartItemId+"";
	return  new ResponseEntity<String>(msg,HttpStatus.OK);
   
   }

	
	@GetMapping("/allcartItems")
	public ResponseEntity<PageableResponse<CartItemDto>> getAllCartItemsByPageable(
			@RequestParam (value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
			@RequestParam (value = "pageSize",defaultValue ="2",required = false)int pageSize,
			@RequestParam (value = "sortBy",defaultValue ="quantity",required = false)String sortBy,
			@RequestParam (value = "sortDir",defaultValue ="asc",required = false)String sortDir
			)
	{
		return new  ResponseEntity<PageableResponse<CartItemDto>>(cartItemService.getAll(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);

	}
}
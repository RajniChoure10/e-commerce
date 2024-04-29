package com.dollop.app.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.dtos.OrderDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.services.IOrderService;

@RestController
@RequestMapping("/Order")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@PostMapping("/{userId}")
	public ResponseEntity<OrderDto> saveOrder(@Validated @RequestBody OrderDto orderDto, @PathVariable String userId) {
		return new ResponseEntity<OrderDto>(orderService.createOrder(orderDto, userId), HttpStatus.OK);
	}

	@PutMapping("/{orderId}/{userId}")
	public ResponseEntity<OrderDto> editOrder(@Validated  @RequestBody OrderDto orderDto, @PathVariable String orderId,
			@PathVariable String userId) {
		return new ResponseEntity<OrderDto>(orderService.updateOrderDto(orderDto, orderId, userId), HttpStatus.OK);
	}

	// problem in delete ..cascasde type when we are delete an order user is also deleted
	@DeleteMapping("/{orderId}/{userId}")
	public ResponseEntity<String> removeOrder(@PathVariable String orderId, @PathVariable String userId) {
		return new ResponseEntity<String>(orderService.deleteOrder(orderId, userId), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<OrderDto>> fetchAllOrderOfUser(@PathVariable String userId)
	{
		 List<OrderDto> orderDtos = orderService.getAllOrderOfUser(userId);
		    return new ResponseEntity<>(orderDtos, HttpStatus.OK);
		
	}
	
	@GetMapping("/allProducts")
	public ResponseEntity<PageableResponse<OrderDto>> getAllProducts(
			@RequestParam (value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
			@RequestParam (value = "pageSize",defaultValue ="2",required = false)int pageSize,
			@RequestParam (value = "sortBy",defaultValue ="orderDate",required = false)String sortBy,
			@RequestParam (value = "sortDir",defaultValue ="asc",required = false)String sortDir
			)
	{
		return new  ResponseEntity<PageableResponse<OrderDto>>(
			orderService.getAllOrder(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
		
	}
	
		
	

}

package com.dollop.app.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dollop.app.dtos.OrderDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.ProductDto;
import com.dollop.app.entities.Order;
import com.dollop.app.entities.Product;
import com.dollop.app.entities.User;
import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.helper.MyHelper;
import com.dollop.app.repositories.OrderRepository;
import com.dollop.app.repositories.UserRepository;
import com.dollop.app.services.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository urepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDto createOrder(OrderDto orderDto, String userId) {
		User user = urepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Id is not found to create order"));

		Order order = new Order();
		order.setOrderId(UUID.randomUUID().toString());
		order.setOrderStatus(orderDto.getOrderStatus());
		order.setOrderAmount(orderDto.getOrderAmount());
		order.setPaymentStatus(orderDto.getPaymentStatus());
		order.setBilingAddress(orderDto.getBilingAddress());
		order.setBilingPhone(orderDto.getBilingPhone());
		order.setBilingName(orderDto.getBilingName());
		order.setOrderDate(orderDto.getOrderDate());
		order.setDeliverDate(orderDto.getDeliverDate());
		order.setUser(user);
		Order order1 = orderRepository.save(order);
		return modelMapper.map(order1, OrderDto.class);

	}

	@Override
	public OrderDto updateOrderDto(OrderDto orderDto, String orderId, String userId) {
		// System.out.println(userId);
		if (orderDto == null) {
			throw new ResourceNotFoundException("OrderDto cannot be null");
		}

		if (orderId == null || orderId.isEmpty()) {
			throw new IllegalArgumentException("orderId " + orderId + "cannot be null or empty");
		}

		if (userId == null || userId.isEmpty()) {
			throw new IllegalArgumentException("userId " + userId + "cannot be null or empty");
		}

		User userId1 = urepo.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("user id " + userId + " not exist to update the order"));
		System.out.println(userId);
		Order order1 = orderRepository.findById(orderId).orElseThrow(
				() -> new ResourceNotFoundException("user id " + orderId + " not exist to update the order"));

		order1.setOrderStatus(orderDto.getOrderStatus());
		order1.setOrderAmount(orderDto.getOrderAmount());
		order1.setPaymentStatus(orderDto.getPaymentStatus());
		order1.setBilingAddress(orderDto.getBilingAddress());
		order1.setBilingPhone(orderDto.getBilingPhone());
		order1.setBilingName(orderDto.getBilingName());
		order1.setOrderDate(orderDto.getOrderDate());
		order1.setDeliverDate(orderDto.getDeliverDate());
		order1.setUser(userId1);
		orderRepository.save(order1);
		return modelMapper.map(order1, OrderDto.class);
	}

	@Override
	public String deleteOrder(String orderId, String userId) {

		if (orderId == null || orderId.isEmpty()) {
			throw new IllegalArgumentException("orderId " + orderId + "cannot be null or empty");
		}

		if (userId == null || userId.isEmpty()) {
			throw new IllegalArgumentException("userId " + userId + "cannot be null or empty");
		}

		Optional<Order> optionalOrder = orderRepository.findById(orderId);

		Order order = optionalOrder
				.orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " not found"));

		if (!order.getUser().getUserId().equals(userId)) {
			throw new ResourceNotFoundException("User and order id  not match to delete this order");
		}
		orderRepository.delete(order);
		return "order deleted with this " + order + "id";

	}

	@Override
	public List<OrderDto> getAllOrderOfUser(String userId) {
		List<Order> orderList = orderRepository.findByUserId(userId);
		if (orderList.isEmpty()) {
			throw new ResourceNotFoundException("No orders available for user with this id " + userId);
		}
		return orderList.stream().map(orders -> modelMapper.map(orders, OrderDto.class)).collect(Collectors.toList());
	}

	@Override
	public PageableResponse<OrderDto> getAllOrder(
			Integer pageNumber, Integer pageSize, String sortBy, String sortDir) 
	{
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? 
				Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Order> page = orderRepository.findAll(pageable);
		PageableResponse<OrderDto> response = MyHelper.getPageableResponse(page, OrderDto.class);
		return response;
	}

}

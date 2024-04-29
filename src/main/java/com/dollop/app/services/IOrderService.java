package com.dollop.app.services;

import java.util.List;

import com.dollop.app.dtos.OrderDto;
import com.dollop.app.dtos.PageableResponse;

public interface IOrderService {

	public  OrderDto createOrder(OrderDto orderDto,String userId);
	public OrderDto updateOrderDto(OrderDto orderDto,String orderId,String userId);
	public String deleteOrder(String orderId,String userId);
	public List<OrderDto>  getAllOrderOfUser(String userId);
	public PageableResponse<OrderDto> getAllOrder(
			Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//create,remove,getOrderByUser,getAllOrders,updateOrder
}

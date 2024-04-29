package com.dollop.app.dtos;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class OrderItemDto {

	private String orderItemId;
	private Integer quantity;
	private Integer totalPrice;
}

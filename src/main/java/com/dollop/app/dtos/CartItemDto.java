package com.dollop.app.dtos;

import com.dollop.app.entities.Cart;
import com.dollop.app.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CartItemDto {

	private String cartItemId;
	
	@Size(min=1,max=100,message="Out of quantity!!")
	@NonNull
	@NotBlank(message="cart item quantity required")
	private Integer quantity;
	
	@NotNull
	@NotBlank(message="enter cart_item totalPrice required")
	private double totalPrice;
	
	
	@JsonIgnoreProperties(value="cartItemId")
	private Cart cart;
	
	@JsonIgnoreProperties(value="cartItemId")
	private Product product;
}

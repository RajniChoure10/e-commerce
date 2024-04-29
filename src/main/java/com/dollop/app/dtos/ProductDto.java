package com.dollop.app.dtos;

import java.sql.Date;

import com.dollop.app.entities.Category;
import com.dollop.app.validate.ImageNameValid;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductDto {

	private String productId;
	
	@NotBlank(message = "product title required..kindly enter")
	@NotNull
	@Size(min=3,max=500,message="Enter Product title!!")
	private String title;
	
	@NotNull
	@Size(min=3,max=500,message="Product description out of size!!")
	@NotBlank(message=" Enter description required")
	private String description;
	
	@NotNull
	@NotBlank(message="price is required to given on category")
	private double price;
	
	private double discountedPrice ;
	
	@NotNull
	@NotBlank(message="necessary to mention Quantity")
	private Integer Quantity;
	
	@NotNull
	@NotBlank(message = "enter product is live or not")
	private Boolean live;
	
	@NotBlank(message = "enter product is in stock or not")
	private Boolean stock;
	
	@ImageNameValid(message="invalid productCategoryImage for product")
	private String productCategoryImage;
	
	private Date addedDate;
	
	@JsonIgnore
	private Category category;

	
	 
}

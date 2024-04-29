package com.dollop.app.entities;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name="products")
public class Product {

	@Id
	private String productId;
	private String title;
	
	@Column(length = 1000)
	private String description;
	private Double price;
	private Double discountedPrice ;
	private Integer quantity;
	
	
	@CreationTimestamp
	private Date addedDate;
	private Boolean live;
	private Boolean stock;
	private String productCategoryImage;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="category_id")
	private Category category;
	
	
}

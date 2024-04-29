package com.dollop.app.dtos;

import com.dollop.app.validate.ImageNameValid;

import jakarta.validation.constraints.NotBlank;
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
public class CategoryDto {

		private String categoryId;
		
		@Size(min=3,max=30,message="invalid name!!")
		private String title;
		
		@NotBlank(message="description required")
		private String description;
		
		@ImageNameValid(message="invalid cover Image for categories")
		private String coverImage;
		
		
}

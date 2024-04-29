package com.dollop.app.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageResponse {
   
	private String imageName;
	private String message;
	 private Boolean success;
	 private HttpStatus status;
	 private List<String> multipleImages;
}

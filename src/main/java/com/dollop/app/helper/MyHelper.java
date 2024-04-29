package com.dollop.app.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.UserDto;
import com.dollop.app.entities.User;

public class MyHelper {

	public static<U,V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
	List<U> entity = page.getContent();
	List<V> dtoList = entity.stream().map(object->new ModelMapper()
			.map(object, type))
			.collect(Collectors.toList());
	
	PageableResponse<V> response = new PageableResponse<>();
	response.setContent(dtoList);
	response.setPageNumber(page.getNumber());
	response.setPageNumber(page.getSize());
	response.setTotalElements(page.getTotalElements());
	response.setTotalPages(page.getTotalPages());
	response.setLastPage(page.isLast());
		
		return response;
	}

}

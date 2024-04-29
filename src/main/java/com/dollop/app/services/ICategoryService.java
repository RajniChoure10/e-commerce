package com.dollop.app.services;



import com.dollop.app.dtos.CategoryDto;
import com.dollop.app.dtos.PageableResponse;

public interface ICategoryService {

	public CategoryDto create(CategoryDto categoryDto);
	public CategoryDto update(CategoryDto categoryDto,String categoryId);
	public void delete(String categoryId);
	PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
    public CategoryDto get(String categoryId);
    
    

}
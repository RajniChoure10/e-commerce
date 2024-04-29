package com.dollop.app.services;

import java.util.List;

import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.ProductDto;

public interface IProductService {

	public ProductDto createProduct(ProductDto productDto, String categoryId);

	public ProductDto updateProduct(ProductDto productDto, String productId);

	public void deleteProduct(String productId);

	public ProductDto getOneProduct(String productId);
    
	public List<ProductDto> getAll();
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

	public PageableResponse<ProductDto> getAllLive(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	public PageableResponse<ProductDto> getAllProductOfCategory(String categoryId,Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir);

	public PageableResponse<ProductDto> searchByTitle(String categoryTitle,Integer pageNumber,Integer pageSize, String sortBy, String sortDir);

	public ProductDto updateProductCategory(ProductDto productDto,String categoryId,String productId);




}

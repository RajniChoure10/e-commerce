
package com.dollop.app.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.ProductDto;

import com.dollop.app.entities.Category;
import com.dollop.app.entities.Product;
import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.helper.MyHelper;
import com.dollop.app.repositories.CategoryRepository;
import com.dollop.app.repositories.ProductRepository;
import com.dollop.app.services.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository prepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepository crepo;

	@Override
	public ProductDto createProduct(ProductDto productDto, String categoryId) {

		Category category = crepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category id is not found to create product"));

		String productId = UUID.randomUUID().toString();
		productDto.setProductId(productId);
		productDto.setCategory(category);
		Product product = dtoToEntity(productDto);
		Product savedProduct = prepo.save(product);

		ProductDto newDto = entityToDto(savedProduct);
		return newDto;
	}

	private ProductDto entityToDto(Product savedProduct) {
		return modelMapper.map(savedProduct, ProductDto.class);
	}

	private Product dtoToEntity(ProductDto productDto) {
		return modelMapper.map(productDto, Product.class);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, String productId) {
		Product product = prepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product id  not found for given user id"));
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		product.setAddedDate(productDto.getAddedDate());
		product.setLive(productDto.getLive());
		product.setStock(productDto.getStock());
		product.setProductCategoryImage(productDto.getProductCategoryImage());

		product = prepo.save(product);
		return modelMapper.map(product, ProductDto.class);

	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = prepo.findAll(pageable);
		PageableResponse<ProductDto> response = MyHelper.getPageableResponse(page, ProductDto.class);
		return response;

	}

	@Override
	public void deleteProduct(String productId) {
		ProductDto productDto = getOneProduct(productId);
		Product product = modelMapper.map(productDto, Product.class);
		prepo.delete(product);

	}

	public ProductDto getOneProduct(String productId) {
		Product product = prepo.findById(productId).get();
		return modelMapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = prepo.findByLiveIsFalse(pageable);
		PageableResponse<ProductDto> response = MyHelper.getPageableResponse(page, ProductDto.class);
		return response;

	}

	@Override
	public ProductDto updateProductCategory(ProductDto productDto, String categoryId, String productId) {

		if (productDto == null) {
			throw new ResourceNotFoundException("ProductDto cannot be null");
		}

		if (categoryId == null || categoryId.isEmpty()) {
			throw new IllegalArgumentException("Category ID cannot be null or empty");
		}

		if (productId == null || productId.isEmpty()) {
			throw new IllegalArgumentException("Product ID cannot be null or empty");

		}
		Category category = crepo.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

		Product product = prepo.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + productDto.getProductId()));

		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		product.setAddedDate(productDto.getAddedDate());
		product.setLive(productDto.getLive());
		product.setStock(productDto.getStock());
		product.setProductCategoryImage(productDto.getProductCategoryImage());
		product.setCategory(category);
		prepo.save(product);
		return modelMapper.map(product, ProductDto.class);

	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String categoryTitle, Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = prepo.findByTitle(categoryTitle, pageable);
		PageableResponse<ProductDto> response = MyHelper.getPageableResponse(page, ProductDto.class);
		return response;

	}

	@Override
	public PageableResponse<ProductDto> getAllProductOfCategory(String categoryId,Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir) {

		Category category = crepo.findById(categoryId)
	    .orElseThrow(() -> new ResourceNotFoundException("Category id "+categoryId+"+is not exists to get product"));
		
		// Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).descending()
		// : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
		Page<Product> productPage = prepo.findByCategory(category, pageable);
		  List<ProductDto> productDtos = productPage.getContent().stream()
		            .map(product -> modelMapper.map(product, ProductDto.class))
		            .collect(Collectors.toList());
		System.out.println(productDtos);

		return new PageableResponse<>(productDtos,productPage.getTotalElements(),
				productPage.getTotalPages(),
				productPage.getNumber(), productPage.getSize());

	}

	@Override
	public List<ProductDto> getAll() {
		List<Product> plist = prepo.findAll();
		List<ProductDto> productDto = plist.stream()
				.map((product)->modelMapper.map(product,ProductDto.class))
	    .collect(Collectors.toList());
		return productDto;
		}
	
	}


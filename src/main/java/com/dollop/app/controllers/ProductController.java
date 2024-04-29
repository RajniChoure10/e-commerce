package com.dollop.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.ProductDto;
import com.dollop.app.services.IProductService;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private IProductService pservice;

	@PostMapping("/{categoryId}")
	ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto, @PathVariable String categoryId) {

		return new ResponseEntity<>(pservice.createProduct(productDto, categoryId), HttpStatus.CREATED);

	}

	@GetMapping("/{productId}")
	ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
		ProductDto productDto = pservice.getOneProduct(productId);
		ResponseEntity<ProductDto> pd = new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
		return pd;

	}

	@PutMapping("/{productId}")
	ResponseEntity<ProductDto> editProduct(@RequestBody ProductDto productDto, @PathVariable String productId) {
		return new ResponseEntity<ProductDto>(pservice.updateProduct(productDto, productId), HttpStatus.OK);

	}

	@DeleteMapping("/{productId}")
	ResponseEntity<ProductDto> removeProduct(@PathVariable String productId) {
		pservice.deleteProduct(productId);
		String msg = "product deleted succesfully";
		return new ResponseEntity<ProductDto>(HttpStatus.OK);
	}

	@GetMapping("/allProductssss")
	public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(

			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,

			@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,

			@RequestParam(value = "sortBy", defaultValue = "price", required = false) String sortBy,

			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return new ResponseEntity<PageableResponse<ProductDto>>(pservice.getAll(pageNumber, pageSize, sortBy, sortDir),
				HttpStatus.OK);
	}
	
	
	  
	  @GetMapping("/allProducts")
	  public ResponseEntity<List<ProductDto>> getAllPro()
	  {  
		  return ResponseEntity.ok(pservice.getAll());
	  }
	  
	 
	
	@GetMapping("/allLiveProducts")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "price", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return new ResponseEntity<PageableResponse<ProductDto>>(
				pservice.getAllLive(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

	}

	@GetMapping("/searchByTitleProducts/{categoryTitle}")
	public ResponseEntity<PageableResponse<ProductDto>> getSearchByTitleProducts(@PathVariable String categoryTitle,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return new ResponseEntity<PageableResponse<ProductDto>>(
				pservice.searchByTitle(categoryTitle, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

	}

	@PutMapping("/updateProductCategory/{categoryId}/{productId}")
	ResponseEntity<ProductDto> editProductCategory(@RequestBody ProductDto productDto, @PathVariable String categoryId,
			@PathVariable String productId) {
		return new ResponseEntity<ProductDto>(pservice.updateProductCategory(productDto, categoryId, productId),
				HttpStatus.OK);

	}

	@GetMapping("/getallPro/{categoryId}")
	public ResponseEntity<PageableResponse<ProductDto>> getProductsByCategoryId(@PathVariable String categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		return new ResponseEntity<PageableResponse<ProductDto>>(
				pservice.getAllProductOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

	}

}

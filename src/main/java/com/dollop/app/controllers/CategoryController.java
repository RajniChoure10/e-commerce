package com.dollop.app.controllers;

import java.io.IOException;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dollop.app.dtos.CategoryDto;
import com.dollop.app.dtos.ImageResponse;
import com.dollop.app.services.ICategoryService;
import com.dollop.app.services.IFileService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IFileService fileService;

	@Value("${category.profile.image.path}")
	private String imagePathValue;

	private CategoryDto readData;

	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.create(categoryDto), HttpStatus.CREATED);

	}

	@GetMapping("/cId")
	public ResponseEntity<CategoryDto> getOne(@PathVariable String cId) {
		CategoryDto categoryDto = categoryService.get(cId);
		ResponseEntity<CategoryDto> cd = new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
		return cd;

	}

	/*
	 * @PutMapping("/cid") public ResponseEntity<CategoryDto> update(
	 * 
	 * @RequestPart CategoryDto categoryDto,
	 * 
	 * @PathVariable String cid,) { return null;
	 */
	

	@DeleteMapping("/{cid}")
	public ResponseEntity<CategoryDto> delete(@PathVariable String cid) {
		categoryService.delete(cid);
		return new ResponseEntity<CategoryDto>(HttpStatus.OK);

	}

	@GetMapping("/getall")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		return null;
	}

	@PostMapping("/imageupload")
	public ResponseEntity<ImageResponse> uploadImage(
			@RequestPart("anyimage") MultipartFile image,
			@RequestPart("category") String category) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		readData = mapper.readValue(category, CategoryDto.class);
		String imageName = fileService.uploadFile(image, imagePathValue);
		readData.setCoverImage(imageName);
		CategoryDto photoId = categoryService.create(readData);

		ImageResponse imageRps = ImageResponse.builder().imageName(imageName).message("image is uploaded").success(true)
				.status(HttpStatus.CREATED).build();
		return new ResponseEntity<ImageResponse>(imageRps, HttpStatus.OK);

	}

	@GetMapping("/{categoryId}")
	public void getFileFromServer(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
		CategoryDto category = categoryService.get(categoryId);
		InputStream resources = fileService.getResource(imagePathValue, category.getCoverImage());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resources, response.getOutputStream());

	}

}

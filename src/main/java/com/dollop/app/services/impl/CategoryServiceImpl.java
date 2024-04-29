package com.dollop.app.services.impl;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dollop.app.dtos.CategoryDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.entities.Category;
import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.helper.MyHelper;
import com.dollop.app.repositories.CategoryRepository;
import com.dollop.app.services.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryRepository crepo;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${category.profile.image.path}")
	private String imagePathValue;

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);

		Category category = dtoToEntity(categoryDto);
		Category savedcategory = crepo.save(category);

		CategoryDto newcategoryDto = entityToDto(savedcategory);
		return newcategoryDto;
	}

	private Category dtoToEntity(CategoryDto categoryDto) {

		return modelMapper.map(categoryDto, Category.class);
	}

	private CategoryDto entityToDto(Category savedcategory) {
		return modelMapper.map(savedcategory, CategoryDto.class);
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
		Category category = crepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Id is not found to update category"));
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		category.setCoverImage(categoryDto.getCoverImage());

		category = crepo.save(category);
		return modelMapper.map(category, CategoryDto.class);

	}

	@Override
	public void delete(String categoryId) {
		CategoryDto categoryDto = get(categoryId);
		Category category = modelMapper.map(categoryDto, Category.class);
		crepo.delete(category);
	}

	public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) 
	{
		org.springframework.data.domain.Sort sort = (sortDir.equalsIgnoreCase("asc"))
				? (org.springframework.data.domain.Sort.by(sortBy).descending())
				: (org.springframework.data.domain.Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Category> page = crepo.findAll(pageable);
		PageableResponse<CategoryDto> response = MyHelper.getPageableResponse(page, CategoryDto.class);
		return response;
	}

	public CategoryDto get(String categoryId) {
		Category c = crepo.findById(categoryId).get();
		return modelMapper.map(c, CategoryDto.class);

	}

}

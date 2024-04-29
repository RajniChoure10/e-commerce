package com.dollop.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.app.entities.Category;
import com.dollop.app.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	 // @Query("SELECT p FROM Product p JOIN p.category c WHERE c.category.id =:categoryId ")
	@Query("select p from Product p WHERE p.category =:category")
	  Page<Product> findByCategory(Category category,Pageable pageable);
	  
	  @Query("SELECT p FROM Product p JOIN p.category c WHERE c.title = :categoryTitle")
	  Page<Product> findByTitle(String categoryTitle,Pageable pageable);
	  
		/* @Query("select p from Product p where p.live=true") */
	  Page<Product> findByLiveIsTrue(Pageable pageable);
	  
	  Page<Product> findByLiveIsFalse(Pageable pageable);
	  
	  
	   
	 
	 

}

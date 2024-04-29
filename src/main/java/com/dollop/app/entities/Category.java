package com.dollop.app.entities;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name="categories")
public class Category {
    @Id
    @Column(name="id")
	private String categoryId;
    
    @Column(name="category_title",nullable = false)
	private String title;
   
    @Column(name="category_desc",length = 500)
	private String description;
    
    @Column(name="category_cover_image")
	private String coverImage;
	
    @JsonIgnore
    @OneToMany(mappedBy="category" , cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products= new ArrayList<>();
}

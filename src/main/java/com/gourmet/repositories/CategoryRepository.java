package com.gourmet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gourmet.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	public List<Category> findByRestaurantId(Long restaurantId);
}

package com.gourmet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gourmet.model.IngredientsItem;

public interface IngredientItemsRepository extends JpaRepository<IngredientsItem, Long>{

	public List<IngredientsItem> findByRestaurantId(Long id);
}

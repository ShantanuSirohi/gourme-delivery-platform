package com.gourmet.services;

import java.util.List;

import com.gourmet.model.IngredientCategory;
import com.gourmet.model.IngredientsItem;

public interface IngredientsService {

	
	public IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;
	
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception;
	
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;
	
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;
	
	public IngredientsItem createIngredientsItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;

	public IngredientsItem updateStock(Long id) throws Exception; 
	
}
 
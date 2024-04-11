package com.gourmet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gourmet.model.IngredientCategory;
import com.gourmet.model.IngredientsItem;
import com.gourmet.model.Restaurant;
import com.gourmet.repositories.IngredientCategoryRepository;
import com.gourmet.repositories.IngredientItemsRepository;

@Service
public class IngredientsServiceImpl implements IngredientsService{

	@Autowired
	private IngredientItemsRepository ingredientItemsRepository;
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;


	@Autowired
	private RestaurantService restaurantService;
	
	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		
		Restaurant restaurant=restaurantService.findRestaurant(restaurantId);
		IngredientCategory category=new IngredientCategory();
		category.setRestaurant(restaurant);
		category.setName(name);
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		
		Optional<IngredientCategory> optional=ingredientCategoryRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new Exception("Ingredient Category Not Found");
		}
		
		return optional.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
		
		restaurantService.findRestaurant(id);
		return ingredientCategoryRepository.findByRestaurantId(id);  
	}

	@Override
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
		
		return ingredientItemsRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		
		Restaurant restaurant=restaurantService.findRestaurant(restaurantId);
		IngredientCategory category=findIngredientCategoryById(categoryId);
		IngredientsItem item=new IngredientsItem();
		item.setName(ingredientName);
		item.setRestaurant(restaurant);
		item.setCategory(category);
		
		IngredientsItem savedIngredientsItem=ingredientItemsRepository.save(item);
		category.getIngredients().add(savedIngredientsItem);
		return savedIngredientsItem;
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		Optional<IngredientsItem> optionalIngredientItemOptional=ingredientItemsRepository.findById(id);
		if(optionalIngredientItemOptional.isEmpty()) {
			throw new Exception("Ingredient Not Found");
		}
		
		IngredientsItem ingredientsItem=optionalIngredientItemOptional.get();
		ingredientsItem.setInStock(!ingredientsItem.getInStock());
		
		return ingredientItemsRepository.save(ingredientsItem);
	}
}

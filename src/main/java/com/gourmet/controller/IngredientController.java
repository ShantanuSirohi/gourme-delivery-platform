package com.gourmet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gourmet.model.IngredientCategory;
import com.gourmet.model.IngredientsItem;
import com.gourmet.request.IngredientCategoryRequest;
import com.gourmet.request.IngredientItemRequest;
import com.gourmet.services.IngredientsService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

	
	
	@Autowired
	private IngredientsService ingredientsService;
	
	
	//Create a new category
	
	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception{
		IngredientCategory ingredientCategory=ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
		return new ResponseEntity<>(ingredientCategory,HttpStatus.CREATED);
	}

	
	//Create a new ingredient item
	
	@PostMapping()
	public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest req) throws Exception{
		IngredientsItem ingredientsItem=ingredientsService.createIngredientsItem(req.getRestaurantId(),req.getName(),req.getCategoryId()); 
		return new ResponseEntity<>(ingredientsItem,HttpStatus.CREATED);
	}
	
	//Check the stock of the available items

	@PutMapping("/{id}/stock")
	public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) throws Exception{
		IngredientsItem ingredientsItem=ingredientsService.updateStock(id);
		return new ResponseEntity<>(ingredientsItem,HttpStatus.OK);
	}

	//Check the ingredients items in a particular restaurant
	
	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<IngredientsItem >> getRestaurntIngredients(@PathVariable Long id) throws Exception{
		List<IngredientsItem> ingredientItems=ingredientsService.findRestaurantIngredients(id);
		return new ResponseEntity<>(ingredientItems,HttpStatus.OK);
	}

	
	
	@GetMapping("/restaurant/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getRestaurntIngredientCategory(@PathVariable Long id) throws Exception{
		List<IngredientCategory> ingredientCategory=ingredientsService.findIngredientCategoryByRestaurantId(id);
		return new ResponseEntity<>(ingredientCategory,HttpStatus.OK);
	}
}

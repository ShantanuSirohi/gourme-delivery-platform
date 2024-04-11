package com.gourmet.request;

import java.util.List;

import com.gourmet.model.Category;
import com.gourmet.model.IngredientsItem;

import lombok.Data;

@Data
public class CreateFoodRequest {

	private String name;
	
	private String description;
	
	private Long price;
	
	private Category category;
	
	private List<String> images;
	
	private Long restaurantId;
	
	private boolean isVegetarian;
	
	private boolean isSeasonal;
	
	List<IngredientsItem> ingredientsItems;
}

package com.gourmet.request;

import lombok.Data;

@Data
public class IngredientCategoryRequest {

	private String name;
	private Long restaurantId;
}

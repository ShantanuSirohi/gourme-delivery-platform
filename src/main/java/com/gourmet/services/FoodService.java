package com.gourmet.services;

import java.util.List;

import com.gourmet.model.*;
import com.gourmet.request.CreateFoodRequest;

public interface FoodService {
	
	
	//To create a food 
	public Food createFood(CreateFoodRequest req,Category category,Restaurant restaurant);
	
	//To delete the food from the restaurant menu
	public void deleteFood(Long foodId) throws Exception;
	
	
	//To get the list of the food items in a restaurant by using restaurant id
	public List<Food> getRestaurantsFood(Long restaurantId,
										 boolean isVegitarian,
										 boolean isNonveg,
										 boolean isSeasonal,
										 String foodCategory
										 );
	
	//List of the food search by the user using search bar
	public List<Food> searchFood(String keyword);
	
	
	//To search the food using the id
	public Food findFoodById(Long foodId) throws Exception;
	
	
	//To update the status of the availability of the food in the restaurant
	public Food updateAvailabilityStatus(Long foodId) throws Exception;
}

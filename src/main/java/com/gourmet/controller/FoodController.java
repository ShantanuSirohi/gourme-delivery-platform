package com.gourmet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gourmet.model.Food;
import com.gourmet.model.Restaurant;
import com.gourmet.model.User;
import com.gourmet.request.CreateFoodRequest;
import com.gourmet.response.MessageResponse;
import com.gourmet.services.FoodService;
import com.gourmet.services.RestaurantService;
import com.gourmet.services.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
	@Autowired 
	private UserService userService;


	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(@RequestParam String name,@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt); 
		List<Food> foods=foodService.searchFood(name);
		return new ResponseEntity<>(foods,HttpStatus.OK);
	}
	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable("restaurantId") Long restaurantId, 
														@RequestParam Boolean vegetarian,
														@RequestParam Boolean nonVegetarian,
														@RequestParam Boolean seasonal,
														@RequestParam(required = false) String food_category,
														@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		List<Food> foods=foodService.getRestaurantsFood(restaurantId, vegetarian, nonVegetarian, seasonal, food_category);
		return new ResponseEntity<>(foods,HttpStatus.OK);
	}
	
}

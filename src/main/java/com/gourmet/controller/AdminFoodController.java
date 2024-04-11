package com.gourmet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/admin/food")
public class AdminFoodController {

	
	@Autowired
	private FoodService foodService;
	
	@Autowired 
	private UserService userService;


	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantService.findRestaurant(req.getRestaurantId());
		Food food=foodService.createFood(req, req.getCategory(), restaurant);
		return new ResponseEntity<>(food,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable("id") Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		foodService.deleteFood(id);
		MessageResponse messageResponse=new MessageResponse();
		messageResponse.setMessage("Food Deleted from the menu successfully");
		return new ResponseEntity<>(messageResponse,HttpStatus.OK);
	}
	
	@PutMapping("/id")
	public ResponseEntity<Food> updateFoodAvailability(@PathVariable("id") Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		Food food=foodService.updateAvailabilityStatus(id);
		return new ResponseEntity<>(food,HttpStatus.OK);
	}
}

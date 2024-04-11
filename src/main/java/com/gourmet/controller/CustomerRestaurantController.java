package com.gourmet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gourmet.dto.RestaurantDto;
import com.gourmet.model.Restaurant;
import com.gourmet.model.User;
import com.gourmet.services.RestaurantService;
import com.gourmet.services.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class CustomerRestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,@RequestParam String keyword) throws Exception{
		
		List<Restaurant> restaurant=restaurantService.searchRestaurant(keyword);
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt) throws Exception{
		
		List<Restaurant> restaurant=restaurantService.getAllRestaurant();
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt,@PathVariable  Long id) throws Exception{
		
		Restaurant restaurant=restaurantService.findRestaurant(id);
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}/add-favorites")
	public ResponseEntity<RestaurantDto> addToFavorites(@RequestHeader("Authorization") String jwt,@PathVariable  Long id) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		RestaurantDto restaurantDto=restaurantService.addToFavorites(id, user);
		return new ResponseEntity<>(restaurantDto,HttpStatus.OK);
		
	}
	
}

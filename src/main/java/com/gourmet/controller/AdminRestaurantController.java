package com.gourmet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gourmet.model.Restaurant;
import com.gourmet.model.User;
import com.gourmet.request.CreateRestaurantRequest;
import com.gourmet.response.MessageResponse;
import com.gourmet.services.RestaurantService;
import com.gourmet.services.UserService;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantService.createRestaurant(req, user);
		return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req,@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception{
		
		Restaurant restaurant=restaurantService.updateRestaurant(id, req);
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception{
		
		restaurantService.deleteRestaurant(id);
		MessageResponse messageResponse=new MessageResponse();
		messageResponse.setMessage("Restaurant Deleted");
		return new ResponseEntity<>(messageResponse,HttpStatus.OK);
		
	}
	
	
	@PutMapping("/update/{id}/status")
	public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception{
		 
		Restaurant restaurant=restaurantService.updateRestaurantStatus(id);
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String jwt) throws Exception{
		 
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantService.getRestaurantByUserId((long) user.getId()) ;
		return new ResponseEntity<>(restaurant,HttpStatus.OK);	
	}
	
}

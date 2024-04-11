package com.gourmet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gourmet.dto.RestaurantDto;
import com.gourmet.model.Restaurant;
import com.gourmet.model.User;
import com.gourmet.request.CreateRestaurantRequest;

@Service
public interface RestaurantService {

	//To create restaurant request method
	
	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
	
	//To create a update restaurant request method
	
	public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedReq) throws Exception;

	//To delete restaurant
	
	public void deleteRestaurant(Long restaurantId) throws Exception;
	
	//To get list of restaurants only for admins
	
	public List<Restaurant> getAllRestaurant();
	
	//To search restaurant
	
	public List<Restaurant> searchRestaurant(String keyword);
	
	//To search restaurant by id
	
	public Restaurant findRestaurant(Long restaurantId) throws Exception;
	
	//get restaurant by User Id
	
	public Restaurant getRestaurantByUserId(Long userId) throws Exception;
	
	//Add fav restaurant
	
	public RestaurantDto addToFavorites(Long restaurantId,User user) throws Exception;
	
	//Update restaurant status
	
	public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}

package com.gourmet.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gourmet.dto.RestaurantDto;
import com.gourmet.model.Address;
import com.gourmet.model.Restaurant;
import com.gourmet.model.User;
import com.gourmet.repositories.AddressRepository;
import com.gourmet.repositories.RestaurantRepository;
import com.gourmet.repositories.UserRepository;
import com.gourmet.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService{

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		
		Address address=addressRepository.save(req.getAddress());
		Restaurant restaurant=new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedReq) throws Exception {
		
		Restaurant restaurant=findRestaurant(restaurantId);
		if(restaurant.getCuisineType()!=null) {
			restaurant.setCuisineType(updatedReq.getCuisineType());
		}
		if(restaurant.getDescription()!=null) {
			restaurant.setDescription(updatedReq.getDescription());
		}
		if(restaurant.getName()!=null) {
			restaurant.setName(updatedReq.getName());
		}
		return restaurantRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		
		Restaurant restaurant=findRestaurant(restaurantId);
		restaurantRepository.delete(restaurant);
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		
		return restaurantRepository.findAll();
	}

	public List<Restaurant> searchRestaurant(String keyword) {
		
		restaurantRepository.findBySearchQuery(keyword);
		return null;
	}

	@Override
	public Restaurant findRestaurant(Long restaurantId) throws Exception {
		Optional<Restaurant> optional=restaurantRepository.findById(restaurantId);
		if(optional.isEmpty()) {
			throw new Exception("Restaurant not found with id:"+restaurantId);
		}
		return optional.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
		if(restaurant==null) {
			throw new Exception("Restaurant not found with Owner Id "+userId);
		}
		return restaurant;
	}

	@Override
	public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
		Restaurant restaurant=findRestaurant(restaurantId);
		RestaurantDto dto=new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImage(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurant.getId());
		 
		boolean isFavorited=false;
		List<RestaurantDto> favorites=user.getFavoritesList();
		for(RestaurantDto favorite : favorites) {
			if(favorite.getId() == restaurantId.longValue()) {
				isFavorited=true;
				break;
			}
		}
		
		if(isFavorited) {
			favorites.removeIf(favorite -> favorite.getId() == restaurantId.longValue());
		}else {
			favorites.add(dto);
		}
		
		userRepository.save(user);
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
		Restaurant restaurant=findRestaurant(restaurantId);
		restaurant.setOpen(!restaurant.isOpen());
		return restaurantRepository.save(restaurant);
	}

}

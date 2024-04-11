package com.gourmet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gourmet.model.Category;
import com.gourmet.model.Restaurant;
import com.gourmet.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
		Category category=new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantService.getRestaurantByUserId(id);
		return categoryRepository.findByRestaurantId(id);
	}

	@Override
	public Category findCategoryById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Category> optionalCategory=categoryRepository.findById(id);
		if(optionalCategory.isEmpty()) {
			throw new Exception("Category Not Found..... ");
		}
		return optionalCategory.get();
	}

}

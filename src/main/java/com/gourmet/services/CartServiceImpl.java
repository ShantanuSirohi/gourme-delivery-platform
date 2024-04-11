package com.gourmet.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.stereotype.Service;

import com.gourmet.model.Cart;
import com.gourmet.model.CartItem;
import com.gourmet.model.Food;
import com.gourmet.model.User;
import com.gourmet.repositories.CartItemRepository;
import com.gourmet.repositories.CartRepository;
import com.gourmet.request.AddCartItemRequest;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		// TODO Auto-generated method stub
		User user=userService.findUserByJwtToken(jwt);
		Food food=foodService.findFoodById(req.getFoodId());
		Cart cart=cartRepository.findByCustomerId((long) user.getId());
		
		
		//If the user has already has selected the food item than has already present in the cart of the user then this code will change the quantity of that food item in the user's cart.
		for(CartItem cartItem:cart.getItems()) {
			if(cartItem.getFood().equals(food)) {
				int newQuantity=cartItem.getQuantity()+req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(),newQuantity);
			}
		}
	
		CartItem newCartItem=new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngredients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());
		
		CartItem savedCartItem=cartItemRepository.save(newCartItem);
		
		//have to save this item to cart as well
		cart.getItems().add(savedCartItem);
		return savedCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int newQuantity) throws Exception {
		// TODO Auto-generated method stub
		Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
		if(cartItemOptional.isEmpty()) {
			throw new Exception("Cart Item Not Found!!"); 
		}
		CartItem item=cartItemOptional.get();
		item.setQuantity(newQuantity);
		item.setTotalPrice(item.getFood().getPrice()*newQuantity); 				//5*100=500
		return cartItemRepository.save(item);
	}

	@Override
	public Cart removeCartItem(Long cartItemId, String jwt) throws Exception {
		// TODO Auto-generated method stub
		User user=userService.findUserByJwtToken(jwt);
		Cart cart=cartRepository.findByCustomerId((long) user.getId());
		Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
		if(cartItemOptional.isEmpty()) {
			throw new Exception("Cart Item Not Found!!"); 
		}
		
		CartItem item=cartItemOptional.get();
		cart.getItems().remove(item);
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		// TODO Auto-generated method stub
		Long total=0L;
		for(CartItem cartItem:cart.getItems()) {
			total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Cart> cartOptional=cartRepository.findById(id);
		if(cartOptional.isEmpty()) { 
			throw new Exception("Cart Not Found With Id:"+id); 
		}
		 
		return cartOptional.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		Cart cart= cartRepository.findByCustomerId(userId);
		cart.setTotal(calculateCartTotals(cart));
		return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		Cart cart=findCartByUserId(userId);
		cart.getItems().clear();
		return cartRepository.save(cart);
	}

}

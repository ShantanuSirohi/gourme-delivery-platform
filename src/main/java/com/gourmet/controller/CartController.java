package com.gourmet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gourmet.model.Cart;
import com.gourmet.model.CartItem;
import com.gourmet.model.User;
import com.gourmet.request.AddCartItemRequest;
import com.gourmet.request.UpdateCartItemRequest;
import com.gourmet.services.CartService;
import com.gourmet.services.UserService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/cart/add")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		CartItem cartItem=cartService.addItemToCart(req, jwt);
		return new ResponseEntity<>(cartItem,HttpStatus.OK);
	}
	
	@PutMapping("/cart-item/update")
	public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		CartItem cartItem=cartService.updateCartItemQuantity(req.getCartItemId(), req.getQty());
		return new ResponseEntity<>(cartItem,HttpStatus.OK);
	}
	
	@DeleteMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeCartItem(@PathVariable("id") Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		Cart cart=cartService.removeCartItem(id, jwt);
		return new ResponseEntity<>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Cart cart=cartService.clearCart((long) user.getId());
		return new ResponseEntity<>(cart,HttpStatus.OK);
	}
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Cart cart=cartService.findCartByUserId((long) user.getId());
		return new ResponseEntity<>(cart,HttpStatus.OK);
	}
}

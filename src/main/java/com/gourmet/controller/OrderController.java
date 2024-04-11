package com.gourmet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gourmet.model.Order;
import com.gourmet.model.User;
import com.gourmet.request.OrderRequest;
import com.gourmet.services.OrderService;
import com.gourmet.services.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,@RequestHeader("AUthorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Order order=orderService.createOrder(req, user);
		return new ResponseEntity<>(order,HttpStatus.CREATED);
	}

	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("AUthorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		List<Order> orders=orderService.getUsersOrder((long) user.getId());
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
}

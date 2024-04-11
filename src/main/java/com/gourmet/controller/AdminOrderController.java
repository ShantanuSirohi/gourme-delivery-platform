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

import com.gourmet.model.Order;
import com.gourmet.model.User;
import com.gourmet.services.OrderService;
import com.gourmet.services.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("AUthorization") String jwt,@PathVariable("id")Long id,@RequestParam(required=false)String order_status) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		List<Order> orders=orderService.getRestaurantsOrder(id, order_status); 
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@PutMapping("/order/{orderId}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(@RequestHeader("AUthorization") String jwt,@PathVariable("orderId")Long orderId,@PathVariable("orderStatus") String order_status) throws Exception{
		Order order=orderService.updateOrder(orderId, order_status);
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
}

package com.gourmet.services;

import java.util.List;

import com.gourmet.model.Order;
import com.gourmet.model.User;
import com.gourmet.request.OrderRequest;

public interface OrderService {

	public Order createOrder(OrderRequest req,User user) throws Exception;
	
	public Order updateOrder(Long orderId,String orderStatus) throws Exception;
	
	public void cancelOrder(Long orderId) throws Exception;
	
	public List<Order> getUsersOrder(Long userId) throws Exception;
	
	public List<Order> getRestaurantsOrder(Long restaurantId,String orderStatus) throws Exception;
	
	public Order findOrderById(Long orderId) throws Exception;
	
}

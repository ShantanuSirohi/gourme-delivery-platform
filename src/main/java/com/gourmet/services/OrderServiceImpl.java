package com.gourmet.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gourmet.model.Address;
import com.gourmet.model.Cart;
import com.gourmet.model.CartItem;
import com.gourmet.model.Order;
import com.gourmet.model.OrderItem;
import com.gourmet.model.Restaurant;
import com.gourmet.model.User;
import com.gourmet.repositories.AddressRepository;
import com.gourmet.repositories.OrderItemRepository;
import com.gourmet.repositories.OrderRepository;
import com.gourmet.repositories.RestaurantRepository;
import com.gourmet.repositories.UserRepository;
import com.gourmet.request.OrderRequest;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public Order createOrder(OrderRequest req, User user) throws Exception {
		Address shippingAddress=req.getDeliveryAddress();
		Address savedAddress=addressRepository.save(shippingAddress);
		if(!user.getAddresses().contains(savedAddress)) {
			 user.getAddresses().add(savedAddress);
			 userRepository.save(user);
		}
		Restaurant restaurant=restaurantService.findRestaurant(req.getRestaurantId());
		Order createdOrder=new Order();
		createdOrder.setCustomer(user);
		createdOrder.setCreateAt((Date) new java.util.Date());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.setDeliveryAddress(savedAddress);
		createdOrder.setRestaurant(restaurant);
		
		Cart cart=cartService.findCartByUserId((long) user.getId());
		
		List<OrderItem> orderItems=new ArrayList<>();
		for(CartItem cartItem:cart.getItems()) {
			OrderItem orderItem=new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			
			OrderItem saveOrderItem=orderItemRepository.save(orderItem);
			orderItems.add(saveOrderItem);
		}
		Long totalPrice=cartService.calculateCartTotals(cart);
		
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(totalPrice);
		
		Order savedOrder=orderRepository.save(createdOrder);
		restaurant.getOrders().add(savedOrder);
		
		return createdOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		Order order=findOrderById(orderId);
		if(orderStatus.equals("OUT_FOR_DElivery")||orderStatus.equals("DELIVERED")||orderStatus.equals("COMPLETED")||orderStatus.equals("PENDING")) {
			order.setOrderStatus(orderStatus);
			return orderRepository.save(order);
		}
		throw new Exception("Please select a valid order status");
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		Order order=findOrderById(orderId);
		orderRepository.deleteById(orderId);
	}

	@Override
	public List<Order> getUsersOrder(Long userId) throws Exception {
		return orderRepository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
		List<Order> orders=orderRepository.findByRestaurantId(restaurantId);
		if(orderStatus!=null) {
			orders=orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
		}
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		Optional<Order> optionalOrder=orderRepository.findById(orderId);
		if(optionalOrder.isEmpty()) {
			throw new Exception("order not found");
		}
		return optionalOrder.get();
	}

}

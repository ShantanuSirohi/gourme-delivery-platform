package com.gourmet.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User customer;

	@JsonIgnore
	@ManyToOne 
	private Restaurant  restaurant;

	private Long totalAmount;
	
	private String orderStatus;
	
	private Date createAt;
	
	@ManyToOne
	private Address deliveryAddress;

	@OneToMany
	private List<OrderItem> orderItems=new ArrayList<>();
	
//	private Payment paymentOptions;
	
	private int totalItem;
	
	private Long totalPrice;
}

package com.gourmet.request;

import com.gourmet.model.Address;

import lombok.Data;

@Data
public class OrderRequest {

	private Long restaurantId;
	private Address deliveryAddress;
}

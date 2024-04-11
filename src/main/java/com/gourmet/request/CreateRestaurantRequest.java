package com.gourmet.request;

import java.util.List;

import com.gourmet.model.Address;
import com.gourmet.model.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {

	private Long id;
	private String name;
	private String description;
	private String cuisineType;
	private Address address;
	private ContactInformation contactInformation;
	private String openingHours;
	private List<String> images;
	
}

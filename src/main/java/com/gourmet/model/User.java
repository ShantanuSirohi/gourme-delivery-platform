package com.gourmet.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gourmet.dto.RestaurantDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String fullname;

	private String email;
	
	//Used to hide the password in the frontend
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
//	Create a enum for different type of roles.
	private USER_Role role=USER_Role.ROLE_CUSTOMER;
	
//	Create a class of name Order(the orders made by the user).
	
//	User has one to many relation with order entity.
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
	private List<Order> orders=new ArrayList<>();
	
	
	@ElementCollection
	private List<RestaurantDto> favoritesList=new ArrayList<>();
	 
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
//	CascadeTypeAll because if we delete a user then all the addresses should be deleted from the db.
	private List<Address> addresses=new ArrayList<>();
}

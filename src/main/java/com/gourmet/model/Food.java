package com.gourmet.model;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String description;
	
	private Long price;
	
	@ManyToOne
	private com.gourmet.model.Category foodCategory;
	
	@Column(length = 1000)
	@ElementCollection
	private List<String> images;
	
	private Boolean available;
	
	@ManyToOne
	private Restaurant restaurant;
	
	private Boolean isVegterian;
	
	private Boolean isSeasonal;
	
	@ManyToMany
	private List<IngredientsItem> ingredientsItems=new ArrayList<>();
	
	private Date creationDate;
}

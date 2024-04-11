package com.gourmet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gourmet.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	
}

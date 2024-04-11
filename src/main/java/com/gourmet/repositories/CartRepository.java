package com.gourmet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gourmet.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	public Cart findByCustomerId(Long userId);
}

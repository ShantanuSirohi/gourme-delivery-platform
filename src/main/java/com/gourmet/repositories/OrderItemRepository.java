package com.gourmet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gourmet.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}

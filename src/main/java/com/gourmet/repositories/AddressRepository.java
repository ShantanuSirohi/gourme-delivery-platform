package com.gourmet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gourmet.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long>{

}

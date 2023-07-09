package com.cognizant.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.sales.model.CartDetails;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, String> {


}

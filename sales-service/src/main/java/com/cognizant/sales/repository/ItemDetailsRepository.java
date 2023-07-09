package com.cognizant.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.sales.model.ItemDetails;

@Repository
public interface ItemDetailsRepository extends JpaRepository<ItemDetails, String> {

}

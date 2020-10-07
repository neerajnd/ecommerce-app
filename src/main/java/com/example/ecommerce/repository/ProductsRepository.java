package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.Product;

public interface ProductsRepository extends JpaRepository<Product, Long>{
	
	List<Product> findByIdIn(List<Long> productIds);
}

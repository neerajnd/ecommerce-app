package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{

}

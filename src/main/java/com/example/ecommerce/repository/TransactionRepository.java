package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}

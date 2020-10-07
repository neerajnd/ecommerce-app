package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long>{

}

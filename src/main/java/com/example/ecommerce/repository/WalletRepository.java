package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{

	Wallet findByUserId(Long userId);
}

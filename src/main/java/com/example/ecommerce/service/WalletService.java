package com.example.ecommerce.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.TopUpDTO;
import com.example.ecommerce.model.TransactionType;
import com.example.ecommerce.model.Transaction;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wallet;
import com.example.ecommerce.repository.TransactionRepository;
import com.example.ecommerce.repository.WalletRepository;

@Service
@Transactional
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private TransactionRepository transactionsRepository;

	public ResponseEntity<Object> topupWallet(Long userId, TopUpDTO topUpDTO) {
		
		Wallet wallet = walletRepository.findByUserId(userId);
		wallet.setAmount(topUpDTO.getAmount() + wallet.getAmount());
		walletRepository.save(wallet);
		
		Transaction transactions = new Transaction();
		transactions.setAmount(topUpDTO.getAmount());
		User user = new User();
		user.setId(userId);
		transactions.setUser(user);
		transactions.setTransactionType(TransactionType.TOPUP);
		transactions.setTransactionTime(Instant.now());
		transactionsRepository.save(transactions);
	
		return ResponseEntity.noContent().build();
	}
}

package com.example.ecommerce.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.TopUpDTO;
import com.example.ecommerce.model.TransactionType;
import com.example.ecommerce.model.Transactions;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wallet;
import com.example.ecommerce.repository.TransactionsRepository;
import com.example.ecommerce.repository.WalletRepository;

@Service
@Transactional
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private TransactionsRepository transactionsRepository;

	public void topUpWallet(Long userId, TopUpDTO topUpDTO) {
		
		Wallet wallet = walletRepository.findByUserId(userId);
		wallet.setAmount(topUpDTO.getAmount() + wallet.getAmount());
		walletRepository.save(wallet);
		
		Transactions transactions = new Transactions();
		transactions.setAmount(topUpDTO.getAmount());
		User user = new User();
		user.setId(userId);
		transactions.setUser(user);
		transactions.setTransactionType(TransactionType.TOPUP);
		transactions.setTimestamp(Instant.now());
		transactionsRepository.save(transactions);
	}
}

package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.TopUpDTO;
import com.example.ecommerce.security.UserPrincipal;
import com.example.ecommerce.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@PostMapping("/topup")
	public void topUp(@RequestBody TopUpDTO topUpDTO, Authentication authentication) {
		
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		walletService.topUpWallet(userPrincipal.getId(), topUpDTO);
	}
}

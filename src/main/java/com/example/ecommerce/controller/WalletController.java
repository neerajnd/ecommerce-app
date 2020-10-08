package com.example.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	@PatchMapping("")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public ResponseEntity<Object> topUp(@RequestBody @Valid TopUpDTO topUpDTO, Authentication authentication) {
		
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		return walletService.topupWallet(userPrincipal.getId(), topUpDTO);
	}
}
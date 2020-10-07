package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.security.UserPrincipal;
import com.example.ecommerce.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping("")
	public void createOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) {
		
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		orderService.createOrder(orderDTO, userPrincipal.getId());
	}
}

package com.example.ecommerce.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.ErrorDTO;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.security.UserPrincipal;
import com.example.ecommerce.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping("")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public ResponseEntity<Object> createOrder(@RequestBody @Valid OrderDTO orderDTO, Authentication authentication) {
		
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		return orderService.createOrder(orderDTO, userPrincipal.getId());
	}
	
	@GetMapping("")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public ResponseEntity<?> getOrders(@RequestParam(name = "from") String from, @RequestParam(name = "to") String to, Authentication authentication) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = null, toDate = null;
		try {
			fromDate = sdf.parse(from);
			toDate = sdf.parse(to);
		}
		catch(ParseException pe) {
			return ResponseEntity.badRequest().body(new ErrorDTO("Bad Request", "please pass valid date in format yyyy-mm-dd"));
		}
		
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		return orderService.getOrders(userPrincipal.getId(), fromDate, toDate);
	}
}

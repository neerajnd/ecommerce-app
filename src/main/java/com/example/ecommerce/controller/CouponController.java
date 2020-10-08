package com.example.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.CouponDTO;
import com.example.ecommerce.service.CouponService;

@RestController
@RequestMapping("/coupons")
public class CouponController {
	
	@Autowired
	private CouponService couponService;

	@PostMapping("")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Object> addCoupon(@RequestBody @Valid CouponDTO couponDTO) throws Exception{
		
		return couponService.addCoupon(couponDTO);
	}
}

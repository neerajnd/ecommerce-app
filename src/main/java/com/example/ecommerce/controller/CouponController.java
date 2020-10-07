package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void addCoupon(@RequestBody CouponDTO couponDTO) {
		
		couponService.addCoupon(couponDTO);
	}
}

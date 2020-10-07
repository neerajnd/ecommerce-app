package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.CouponDTO;
import com.example.ecommerce.model.Coupon;
import com.example.ecommerce.repository.CouponRepository;

@Service
@Transactional
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;
	
	public void addCoupon(CouponDTO couponDTO) {
		
		Coupon coupon = new Coupon();
		prepareCoupon(coupon, couponDTO);
		couponRepository.save(coupon);
	}
	
	private void prepareCoupon(Coupon coupon, CouponDTO couponDTO) {
		coupon.setCode(couponDTO.getCode());
		coupon.setValue(couponDTO.getValue());
		coupon.setExpiryDate(couponDTO.getExpiryDate());
	}
}

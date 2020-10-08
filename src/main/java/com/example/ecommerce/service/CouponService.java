package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.CouponDTO;
import com.example.ecommerce.dto.ErrorDTO;
import com.example.ecommerce.model.Coupon;
import com.example.ecommerce.repository.CouponRepository;

@Service
@Transactional
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;
	
	public ResponseEntity<Object> addCoupon(CouponDTO couponDTO) throws Exception {
		
		Coupon coupon = couponRepository.findByCode(couponDTO.getCode());
		if(coupon != null) {
			return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Coupon code already exist"));
		}
		coupon = new Coupon();
		prepareCoupon(coupon, couponDTO);
		couponRepository.save(coupon);
		
		return ResponseEntity.created(null).body(null);
	}
	
	private void prepareCoupon(Coupon coupon, CouponDTO couponDTO) {
		coupon.setCode(couponDTO.getCode());
		coupon.setValue(couponDTO.getValue());
	}
}

package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>{

	@Query(value = "select * from coupon where lower(code) = lower(:code)", nativeQuery = true)
	Coupon findByCode(String code);
}

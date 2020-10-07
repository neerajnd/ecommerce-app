package com.example.ecommerce.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDTO {

	private Long id;
	private String code;
	private Float value;
	private Date expiryDate;
}

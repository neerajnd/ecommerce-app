package com.example.ecommerce.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

	private Long id;
	private List<Long> productIds;
	private String couponCode;
}

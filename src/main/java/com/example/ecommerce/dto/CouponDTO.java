package com.example.ecommerce.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class CouponDTO {

	private Long id;
	@NotBlank(message = "coupon code must not be blank")
	@Size(min = 3, max = 10, message = "length of coupon code must be between 3 and 10")
	private String code;
	@NotNull(message = "value of coupon must not be null")
	@Min(value = 0, message = "value of coupon must be greater than 0")
	private Float value;
}

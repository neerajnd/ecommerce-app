package com.example.ecommerce.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class OrderDTO {

	private Long id;
	@NotNull(message = "Products list must not be null")
	private List<Long> productIds;
	private String couponCode;
	private Date purchaseDate;
	private List<ProductDTO> products;
}

package com.example.ecommerce.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class TopUpDTO {

	@NotNull(message = "Please enter a valid amount greater than 0")
	@Min(value = 1, message = "Topup amount must be greater than or equal to 1")
	private Float amount;
}
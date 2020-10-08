package com.example.ecommerce.dto;

import java.util.Date;

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
public class ProductDTO {

	private Long id;
	@NotBlank(message = "product name must not be blank")
	@Size(min = 1, max = 100, message = "length of product name must be between 1 and 100")
	private String name;
	@NotBlank(message = "product description must not be blank")
	@Size(min = 1, max = 500, message = "length of product description must be between 1 and 500")
	private String description;
	@NotNull(message = "product price must not be blank")
	@Min(value = 0, message = "product's price must be greater than 0")
	private Float price;
	private Boolean isActive;
	private Date purchaseDate;
	private Float percent;
}

package com.example.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	private Long id;
	private String name;
	private String description;
	private Float price;
	private Boolean isActive;
}

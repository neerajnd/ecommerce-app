package com.example.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

	private String error;
	private String message;
	
	public ErrorDTO(String error, String message) {
		super();
		this.error = error;
		this.message = message;
	}
}

package com.example.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDTO {

	private Boolean success;
	private Object data;
	
	public ApiResponseDTO(Boolean success, Object data) {
		super();
		this.success = success;
		this.data = data;
	}
	
}

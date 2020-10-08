package com.example.ecommerce.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class UserDTO {

	private Long id;
	@NotBlank(message = "first name must not be blank")
	@Size(min = 1, max = 20, message = "first name must be 1 to 20 characters long")
	private String firstName;
	@NotBlank(message = "last name must not be blank")
	@Size(min = 1, max = 20, message = "first name must be 1 to 20 characters long")
	private String lastName;
	@NotBlank(message = "email must not be blank")
	@Email
	private String email;
	@NotBlank(message = "password must not be blank")
	@Size(min = 1, max = 20, message = "password must be 5 to 20 characters long")
	private String password;
	
}

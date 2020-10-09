package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.ecommerce.dto.ApiResponseDTO;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public Object getAllEmployees() {
		
		ResponseEntity<Object> response = null;
		try {
			response = restTemplate.getForEntity("http://dummy.restapiexample.com/api/v1/employees", Object.class);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ApiResponseDTO(true, response.getBody());
	}
}

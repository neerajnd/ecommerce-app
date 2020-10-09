package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.ApiResponseDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.security.UserPrincipal;
import com.example.ecommerce.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<ApiResponseDTO> getUser(@PathVariable Long id) {
		
		return userService.getUser(id);
	}
	
	@PutMapping("/users/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		
		return userService.updateUser(id, userDTO);
	}
	
	@DeleteMapping("/users/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		
		return userService.deleteUser(id);
	}
	
	@PutMapping("/user")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public ResponseEntity<Object> updateLoggedInUser(@RequestBody UserDTO userDTO, Authentication authentication) {
		
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		return userService.updateUser(userPrincipal.getId(), userDTO);
	}
	
	@GetMapping("/user")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public ResponseEntity<ApiResponseDTO> getLoggedInUser(Authentication authentication) {
		
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		return userService.getUser(userPrincipal.getId());
	}
}

package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("")
	public void createUser(@RequestBody UserDTO userDTO) {
		
		userService.addUser(userDTO);
	}
	
	@GetMapping("/{id}")
	public UserDTO getUser(@PathVariable Long id) {
		
		return userService.getUser(id);
	}
}

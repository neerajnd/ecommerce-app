package com.example.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.ApiResponseDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productsService;
	
	@PostMapping("")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Object> addProduct(@RequestBody @Valid ProductDTO productDTO) {
		
		return productsService.addProduct(productDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDTO> getProductDetails(@PathVariable Long id) {
		
		return productsService.getProductDetails(id);
	}
	
	@PutMapping("/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Object> updateProductDetails(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
		
		return productsService.updateProduct(id, productDTO);
	}
	
	@DeleteMapping("/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
		
		return productsService.deleteProduct(id);
	}
	
	@GetMapping("")
	public ResponseEntity<ApiResponseDTO> getAllProducts() {
		
		return productsService.getAllProducts();
	}
}

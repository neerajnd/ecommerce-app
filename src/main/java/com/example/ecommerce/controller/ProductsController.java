package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.service.ProductsService;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductsService productsService;
	
	@PostMapping("")
	public void addProduct(@RequestBody ProductDTO productDTO) {
		
		productsService.addProduct(productDTO);
	}
	
	@GetMapping("/{id}")
	public ProductDTO getProductDetails(@PathVariable Long id) {
		
		return productsService.getProductDetails(id);
	}
	
	@PutMapping("/{id}")
	public void updateProductDetails(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
		
		productsService.updateProduct(id, productDTO);
	}
	
	@GetMapping("")
	public List<ProductDTO> getAllProducts() {
		
		return productsService.getAllProducts();
	}
}

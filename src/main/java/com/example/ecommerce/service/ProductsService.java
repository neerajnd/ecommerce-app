package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductsRepository;

@Service
public class ProductsService {
	
	@Autowired
	private ProductsRepository productsRepository;

	public void addProduct(ProductDTO productDTO) {
		
		Product product = new Product();
		prepareProduct(product, productDTO);
		productsRepository.save(product);
	}
	
	public void updateProduct(Long productId, ProductDTO productDTO) {
		
		Optional<Product> productOptional = productsRepository.findById(productId);
		if(!productOptional.isPresent()) {
			//return error
		}
		Product product = productOptional.get();
		prepareProduct(product, productDTO);
	}
	
	public ProductDTO getProductDetails(Long productId) {
		Optional<Product> productOptional = productsRepository.findById(productId);
		if(!productOptional.isPresent()) {
			//return error
		}
		Product product = productOptional.get();
		ProductDTO productDTO = new ProductDTO();
		prepareProductDTO(product, productDTO);
		
		return productDTO;
	}
	
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productsRepository.findAll();
	
		List<ProductDTO> productDTOs = products.stream().map(product -> {
			ProductDTO productDTO = new ProductDTO();
			prepareProductDTO(product, productDTO);
			return productDTO;
		})
		.collect(Collectors.toList());
		
		return productDTOs;
	}
	
	private void prepareProduct(Product product, ProductDTO productDTO){
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setIsActive(productDTO.getIsActive());
		product.setPrice(productDTO.getPrice());
	}
	
	private void prepareProductDTO(Product product, ProductDTO productDTO){
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setIsActive(product.getIsActive());
		productDTO.setPrice(product.getPrice());
	}
}

package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.ApiResponseDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productsRepository;

	public ResponseEntity<Object> addProduct(ProductDTO productDTO) {

		Product product = new Product();
		prepareProduct(product, productDTO);
		productsRepository.save(product);

		return ResponseEntity.created(null).build();
	}

	public ResponseEntity<Object> updateProduct(Long productId, ProductDTO productDTO) {

		Optional<Product> productOptional = productsRepository.findById(productId);
		if (!productOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Product product = productOptional.get();
		prepareProduct(product, productDTO);

		productsRepository.save(product);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<ApiResponseDTO> getProductDetails(Long productId) {
		Optional<Product> productOptional = productsRepository.findById(productId);
		if (!productOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Product product = productOptional.get();
		ProductDTO productDTO = new ProductDTO();
		prepareProductDTO(product, productDTO);

		return ResponseEntity.ok(new ApiResponseDTO(true, productDTO));
	}

	public ResponseEntity<Object> deleteProduct(Long productId) {

		Optional<Product> productOptional = productsRepository.findById(productId);
		if (!productOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Product product = productOptional.get();
		product.setIsActive(false);
		
		productsRepository.save(product);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<ApiResponseDTO> getAllProducts() {
		List<Product> products = productsRepository.findByIsActive(true);

		List<ProductDTO> productDTOs = products.stream().map(product -> {
			ProductDTO productDTO = new ProductDTO();
			prepareProductDTO(product, productDTO);
			return productDTO;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(new ApiResponseDTO(true, productDTOs));
	}

	private void prepareProduct(Product product, ProductDTO productDTO) {
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
	}

	private void prepareProductDTO(Product product, ProductDTO productDTO) {
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
	}
}

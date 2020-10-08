package com.example.ecommerce.model;

import java.util.Date;

public interface ProductDetail {

	Long getProductId();
	String getProductName();
	Float getProductPrice();
	Date getPurchaseDate();
	String getProductDescription();
}

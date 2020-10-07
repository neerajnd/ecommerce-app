package com.example.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.ecommerce.model.audit.DateAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends DateAudit{

	public static final long serialVersionUID = 1l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "description", length = 100)
	private String description;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "is_active")
	private Boolean isActive;
}

package com.example.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.ecommerce.model.audit.DateAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wallet")
@Getter
@Setter
public class Wallet extends DateAudit{

	public static final long serialVersionUID = 1l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "amount")
	private Float amount;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}

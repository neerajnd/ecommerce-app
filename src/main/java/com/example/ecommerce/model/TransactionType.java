package com.example.ecommerce.model;

public enum TransactionType {

	TOPUP("topup"), WITHDRAWAL("withdrawal"), PURCHASE("purchase");
	private String transactionType;
	
	TransactionType(String type) {
		this.transactionType = type;
	}
	
	public String getTransactionType() {
		return this.transactionType;
	}
}

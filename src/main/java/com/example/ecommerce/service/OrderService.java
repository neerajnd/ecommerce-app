package com.example.ecommerce.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.model.Coupon;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductOrder;
import com.example.ecommerce.model.TransactionType;
import com.example.ecommerce.model.Transactions;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wallet;
import com.example.ecommerce.repository.CouponRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductOrderRepository;
import com.example.ecommerce.repository.ProductsRepository;
import com.example.ecommerce.repository.TransactionsRepository;
import com.example.ecommerce.repository.WalletRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
	private ProductsRepository productRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductOrderRepository productOrderRepository;
	
	@Autowired
	private TransactionsRepository transactionRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	public void createOrder(OrderDTO orderDTO, Long userId) {
		
		Float totalPrice = 0.0f;
		//Float discountedPrice = 0.0f;
		Coupon coupon = null;
		
		if(orderDTO.getCouponCode() != null) {
			coupon = couponRepository.findByCode(orderDTO.getCouponCode());
			if(coupon == null) {
				// return error
			}
			if(coupon.getExpiryDate().getTime() > new Date().getTime()) {
				// coupon is expired
			}
		}
		
		List<Product> products = productRepository.findByIdIn(orderDTO.getProductIds());
		totalPrice = products.stream()
			.map(product -> product.getPrice()).reduce(0.0f, (a,b) -> a+b);
		
		if(coupon.getValue() > totalPrice) {
			// coupon cannot be applied
		}
		
		Float discountedPrice = totalPrice - coupon.getValue();
		
		Wallet wallet = walletRepository.findByUserId(userId);
		
		if(wallet.getAmount() < discountedPrice) {
			// insufficient balance
		}
		
		User user = new User();
		user.setId(userId);
		
		Order order = new Order();
		order.setUser(user);
		order.setTotalPrice(totalPrice);
		order.setCoupon(coupon);
		order.setDiscountedPrice(discountedPrice);
		
		orderRepository.save(order);
		
		List<ProductOrder> productOrders = products.stream().map(product -> {
			ProductOrder productOrder = new ProductOrder();
			productOrder.setProduct(product);
			productOrder.setOrder(order);
			productOrder.setProductPrice(product.getPrice());
			return productOrder;
		}).collect(Collectors.toList());
		
		productOrderRepository.saveAll(productOrders);
		
		Transactions transaction = new Transactions();
		transaction.setUser(user);
		transaction.setAmount(discountedPrice);
		transaction.setTransactionType(TransactionType.PURCHASE);
		transaction.setTimestamp(Instant.now());
		transactionRepository.save(transaction);
		
		wallet.setAmount(wallet.getAmount() - discountedPrice);
		walletRepository.save(wallet);
	}
}

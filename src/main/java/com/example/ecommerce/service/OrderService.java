package com.example.ecommerce.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.ErrorDTO;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.PurchaseReportDTO;
import com.example.ecommerce.model.Coupon;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductDetail;
import com.example.ecommerce.model.ProductOrder;
import com.example.ecommerce.model.TransactionType;
import com.example.ecommerce.model.Transaction;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wallet;
import com.example.ecommerce.repository.CouponRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductOrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.TransactionRepository;
import com.example.ecommerce.repository.WalletRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductOrderRepository productOrderRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private WalletRepository walletRepository;

	public ResponseEntity<Object> createOrder(OrderDTO orderDTO, Long userId) {

		Float totalPrice = 0.0f;
		Coupon coupon = null;

		if (orderDTO.getCouponCode() != null) {
			coupon = couponRepository.findByCode(orderDTO.getCouponCode());
			if (coupon == null) {
				return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Invalid coupon"));
			}
		}

		List<Product> products = productRepository.findByIdInAndIsActive(orderDTO.getProductIds(), true);
		totalPrice = products.stream().map(product -> product.getPrice()).reduce(0.0f, (a, b) -> a + b);

		if (coupon.getValue() > totalPrice) {
			return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Coupon amount is greater than total order amount"));
		}

		Float discountedPrice = totalPrice - coupon.getValue();

		Wallet wallet = walletRepository.findByUserId(userId);

		if (wallet.getAmount() < discountedPrice) {
			return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Insufficient wallet balance"));
		}

		User user = new User();
		user.setId(userId);

		Order order = saveOrderDetails(user, totalPrice, coupon);
		saveProductOrder(products, order);
		saveTransactionDetails(user, discountedPrice);
		updateWallet(wallet, discountedPrice);
		
		return ResponseEntity.created(null).body(null);
	}

	public ResponseEntity<PurchaseReportDTO> getOrders(Long userId, Date fromDate, Date toDate) {
		List<ProductDetail> products = orderRepository.getOrdersBetweenDates(userId, fromDate, toDate);
		PurchaseReportDTO purchaseReportDTO = new PurchaseReportDTO();
		
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.CEILING);

		Float totalPriceOfAllOrders = products.stream().map(product -> product.getProductPrice()).reduce(0.0f, (a, b) -> a + b);
		
		List<ProductDTO> productDTOs = new ArrayList<>();
		for (ProductDetail productDetail : products) {

			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(productDetail.getProductId());
			productDTO.setName(productDetail.getProductName());
			productDTO.setDescription(productDetail.getProductDescription());
			productDTO.setPrice(productDetail.getProductPrice());
			productDTO.setPercent(
					Float.parseFloat(df.format((productDetail.getProductPrice() / totalPriceOfAllOrders) * 100)));
			productDTOs.add(productDTO);

		}

		purchaseReportDTO.setProducts(productDTOs);
		purchaseReportDTO.setTotalAmount(totalPriceOfAllOrders);
		return ResponseEntity.ok(purchaseReportDTO);
	}
	
	private Order saveOrderDetails(User user, Float totalPrice, Coupon coupon) {
		
		Order order = new Order();
		order.setUser(user);
		order.setTotalPrice(totalPrice);
		order.setCoupon(coupon);

		orderRepository.save(order);
		
		return order;
	}
	
	private void saveProductOrder(List<Product> products, Order order) {
		
		List<ProductOrder> productOrders = products.stream().map(product -> {
			ProductOrder productOrder = new ProductOrder();
			productOrder.setProduct(product);
			productOrder.setOrder(order);
			productOrder.setProductPrice(product.getPrice());
			return productOrder;
		}).collect(Collectors.toList());

		productOrderRepository.saveAll(productOrders);
	}
	
	private void saveTransactionDetails(User user, Float discountedPrice) {
		
		Transaction transaction = new Transaction();
		transaction.setUser(user);
		transaction.setAmount(discountedPrice);
		transaction.setTransactionType(TransactionType.PURCHASE);
		transaction.setTransactionTime(Instant.now());
		transactionRepository.save(transaction);
	}
	
	private void updateWallet(Wallet wallet, Float discountedPrice) {
		
		wallet.setAmount(wallet.getAmount() - discountedPrice);
		walletRepository.save(wallet);
	}
}

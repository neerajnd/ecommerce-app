package com.example.ecommerce.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.ProductDetail;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query(value = "select p.id as productId, p.name as productName, p.description as productDescription, po.product_price as productPrice, o.created_at as purchaseDate from orders o inner join product_order po on o.id = po.order_id inner join product p on po.product_id = p.id where (date(o.created_at) between :startDate and :endDate) and o.user_id = :userId", nativeQuery = true)
	List<ProductDetail> getOrdersBetweenDates(Long userId, Date startDate, Date endDate);
}

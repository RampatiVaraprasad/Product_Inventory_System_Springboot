package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EntityClass.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findByCategoryIgnoreCase(String category);
	
	List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
	
	boolean existsByProductNameIgnoreCase(String productName);
	
	List<Product> findByStockLessThan(Integer stock);
}

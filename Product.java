package com.example.EntityClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	
	
	@NotBlank(message = "Product name is required")
	@Column(nullable = false)
	private String productName;
	
	
	@NotBlank(message = "Category is required")
	@Column(nullable = false)
	private String category;
	
	
	@NotNull(message = "stock is required")
	@Min(value = 0, message = "Stock can not be negetive")
	@Column(nullable = false)
	private Integer stock;
	
	
	@NotNull(message = "Price is required")
	@Positive(message = "Price must be greater than zero")
	@Column(nullable = false)
	private Double price;


	public Product(Integer productId, @NotBlank(message = "Product name is required") String productName,
			@NotBlank(message = "Category is required") String category,
			@NotNull(message = "stock is required") @Min(value = 0, message = "Stock can not be negetive") Integer stock,
			@NotNull(message = "Price is required") @Positive(message = "Price must be greater than zero") Double price) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.stock = stock;
		this.price = price;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public Integer getStock() {
		return stock;
	}


	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Product() {
		
	}
	
	
	
}

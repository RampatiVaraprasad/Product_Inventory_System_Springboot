package com.example.DtoClass;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
public class ProductDTO {

	private Integer productId;
	
	@NotBlank(message = "Product name is required")
	private String productName;
	
	@NotBlank(message = "Category is required")
	private String category;
	
	@NotNull(message = "Stock is required")
	@Min(value = 0, message = "Stock can not be negative")
	private Integer stock;
	
	@NotNull(message = "Price is required")
	@Positive(message = "Price must be greater than zero")
	private Double price;

	public ProductDTO(Integer productId, @NotBlank(message = "Product name is required") String productName,
			@NotBlank(message = "Category is required") String category,
			@NotNull(message = "Stock is required") @Min(value = 0, message = "Stock can not be negative") Integer stock,
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

	public ProductDTO() {
		
	}
	
	
}

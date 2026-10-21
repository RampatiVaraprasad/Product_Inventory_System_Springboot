package com.example.ServiceInterface;

import java.util.List;

import com.example.DtoClass.ProductDTO;

public interface ProductService {
	
	ProductDTO addProduct(ProductDTO dto);
	
	List<ProductDTO> getAllProducts();
	
	ProductDTO getProductById(Integer id);
	
	ProductDTO updateProduct(Integer id, ProductDTO dto);
	
	void deleteProduct(Integer id);
	
	List<ProductDTO> getProductsByCategory(String category);
	
	List<ProductDTO> sortProductsByPrice();
	
	List<ProductDTO> getLowStockProducts(Integer threshold);
	
	List<ProductDTO> getProductsByPriceRange(Double minPrice, Double maxPrice);

}

package com.example.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DtoClass.ProductDTO;
import com.example.ServiceInterface.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {
	
	private final ProductService service;
	
	
	
	public ProductController(ProductService service) {
		this.service = service;
	}

//	@PostMapping("/products")
//	public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO dto){
//		return ResponseEntity.status(HttpStatus.CREATED).body(service.addProduct(dto));
//		
//	}
	@PostMapping("/products")
	public ResponseEntity<List<ProductDTO>> addProducts(@RequestBody List<@Valid ProductDTO> products){
		List<ProductDTO> saveProducts = products.stream().map(service::addProduct).toList();
		return ResponseEntity.status(HttpStatus.CREATED).body(saveProducts);
		
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		return ResponseEntity.ok(service.getAllProducts());
		
	}
	@GetMapping("/products/1")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable @NotNull @Min(1) Integer id){
		return ResponseEntity.ok(service.getProductById(id));	
	}
	@PutMapping("/products/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable @Min(1) Integer id, @Valid @RequestBody ProductDTO dto){
		return ResponseEntity.ok(service.updateProduct(id, dto));
	}
	
//	@PutMapping("/products")
//	public ResponseEntity<List<ProductDTO>> updateProducts(@RequestBody List<@Valid ProductDTO> products){
//		List<ProductDTO> updatedProducts = products.stream().map(dto -> service.updateProduct(dto.getProductId(), dto)).toList();
//		return ResponseEntity.ok(updatedProducts);
//	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable @Min(1) Integer id){
		service.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/products/category/{category}")
	public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable @NotBlank String category){
		return ResponseEntity.ok(service.getProductsByCategory(category));
		
	}
	@GetMapping("/products/sort/price")
	public ResponseEntity<List<ProductDTO>> sortByPrice(){
		return ResponseEntity.ok(service.sortProductsByPrice());
		
	}
	@GetMapping("/products/low-stock")
	public ResponseEntity<List<ProductDTO>> getLowStock(@RequestParam(defaultValue = "10") @Min(0) Integer threshold){
		return ResponseEntity.ok(service.getLowStockProducts(threshold));
		
	}
	@GetMapping("/products/price-range")
	public ResponseEntity<List<ProductDTO>> getByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice){
		return ResponseEntity.ok(service.getProductsByPriceRange(minPrice, maxPrice));
		
	}

}

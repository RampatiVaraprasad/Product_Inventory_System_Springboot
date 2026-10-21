package com.example.ServiceImplimentation;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Constants.AppConstants;
import com.example.CustomExceptions.DuplicateProductException;
import com.example.DtoClass.ProductDTO;
import com.example.EntityClass.Product;
import com.example.Repository.ProductRepository;
import com.example.ServiceInterface.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository repository;
	
	
	
	public ProductServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}

	private ProductDTO toDto(Product product) {
//		return ProductDTO.builder().productId(product.getProductId()).productName(product.getProductName()).category(product.getCategory()).stock(product.getStock()).price(product.getPrice()).build();
		ProductDTO dto = new ProductDTO();
		dto.setProductId(product.getProductId());
		dto.setProductName(product.getProductName());
		dto.setCategory(product.getCategory());
		dto.setStock(product.getStock());
		dto.setPrice(product.getPrice());
		
		return dto;
	}
	
	private Product toEntity(ProductDTO dto) {
		Product product = new Product();
		
		product.setProductId(dto.getProductId());
		product.setProductName(dto.getProductName());
		product.setCategory(dto.getCategory());
		product.setStock(dto.getStock());
		product.setPrice(dto.getPrice());
		
		return product;
	}
	
	private Product getProductOrThrow(Integer id) {
		return repository.findById(id).orElseThrow(()-> new com.example.CustomExceptions.ProductNotFoundException(AppConstants.PRODUCT_NOT_FOUND + id));
		
	}

	@Override
	public ProductDTO addProduct(ProductDTO dto) {
		if (repository.existsByProductNameIgnoreCase(dto.getProductName().trim())) {
			throw new DuplicateProductException(AppConstants.DUPLICATE_PRODUCT + dto.getProductName()); 
		}
		dto.setProductId(null);
		Product product = toEntity(dto);
		product.setProductName(dto.getProductName().trim());
		product.setCategory(dto.getCategory().trim());
		
		return toDto(repository.save(product));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getAllProducts() {
		return repository.findAll().stream().map(this::toDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDTO getProductById(Integer id) {
		return toDto(getProductOrThrow(id));
	}

	@Override
	public ProductDTO updateProduct(Integer id, ProductDTO dto) {

		Product existing = getProductOrThrow(id);
		
		boolean duplicate = repository.existsByProductNameIgnoreCase(dto.getProductName().trim());
		if (duplicate && !existing.getProductName().equalsIgnoreCase(dto.getProductName().trim())) {
			throw new DuplicateProductException(AppConstants.DUPLICATE_PRODUCT + dto.getProductName());
		}
		existing.setProductName(dto.getProductName().trim());
		existing.setCategory(dto.getCategory().trim());
		existing.setStock(dto.getStock());
		existing.setPrice(dto.getPrice());
		
		return toDto(repository.save(existing));
	}
	
	

	@Override
	public void deleteProduct(Integer id) {
		Product product = getProductOrThrow(id);
		repository.delete(product);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getProductsByCategory(String category) {
		if (category == null || category.isBlank()) {
			throw new IllegalArgumentException("Category can not be empty");
		}
		return repository.findAll().stream().filter(p->p.getCategory().equalsIgnoreCase(category.trim())).map(this::toDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> sortProductsByPrice() {
		return repository.findAll().stream().sorted(Comparator.comparing(Product::getPrice)).map(this::toDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getLowStockProducts(Integer threshold) {
		if (threshold == null || threshold < 0) {
			throw new IllegalArgumentException("Stock Threshold can not be negetive");
		}
		return repository.findAll().stream().filter(p->p.getStock() < threshold).map(this::toDto).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDTO> getProductsByPriceRange(Double minPrice, Double maxPrice) {
		
		if (minPrice == null || maxPrice == null || minPrice <= 0 || maxPrice < minPrice) {
			throw new IllegalArgumentException("Invalid Price Range");
		}
		return repository.findAll().stream().filter(p->p.getPrice() >= minPrice && p.getPrice() <= maxPrice).sorted(Comparator.comparing(Product::getPrice)).map(this::toDto).toList();
	}

}

package com.example.ExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.example.CustomExceptions.DuplicateProductException;
import com.example.CustomExceptions.ProductNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(ProductNotFoundException ex){
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", 404,"error", "Product Not Found", "message", ex.getMessage()));
		
	}
	@ExceptionHandler(DuplicateProductException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateProductException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("status",404,"error", "Product Not Found", "message", ex.getMessage()));
		
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex){
		Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(error -> error.getField(),
				error -> error.getDefaultMessage(), (first, second)-> first, LinkedHashMap::new));
		return ResponseEntity.badRequest().body(Map.of("status",400,"error", "Validation Failed", "messages", errors));
	}
	
	@ExceptionHandler({ConstraintViolationException.class, HandlerMethodValidationException.class})
	public ResponseEntity<Map<String, Object>> handleParameterValidation(Exception ex){
		return ResponseEntity.badRequest().body(Map.of("status",400,"error", "Invalid Request Parameter", "messages", ex.getMessage()));
	}
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex){
		return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Bad Request", "message", ex.getMessage()));
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleOtherErrors(Exception ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", 500, "error", "Internal Server Error", "message",
				"An unexpected error occurred"));
		
	}
	
	public ResponseEntity<Map<String, Object>> handleException(Exception ex){
		ex.printStackTrace();	// shows the actual error in STS console
		
		Map<String, Object> error = new HashMap<>();
		error.put("status", 500);
		error.put("message", ex.getMessage());
		error.put("error", "Internal server error");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	
}

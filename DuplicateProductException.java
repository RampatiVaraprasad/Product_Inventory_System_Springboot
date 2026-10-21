package com.example.CustomExceptions;

public class DuplicateProductException extends RuntimeException{

	public DuplicateProductException(String message) {
		super(message);
	}

	
}

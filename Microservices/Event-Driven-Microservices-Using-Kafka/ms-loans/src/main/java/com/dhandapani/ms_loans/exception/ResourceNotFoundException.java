package com.dhandapani.ms_loans.exception;

/**
 * @author Dhandapani Sudhakar
 */
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String resource, String fieldName, String fieldValue) {
		super(String.format("%s with %s : %s does not exist", resource, fieldName, fieldValue));
	}

}

package com.dhandapani.ms_loans.exception;

/**
 * @author Dhandapani Sudhakar
 */
public class ResourceAlreadyExistsException extends RuntimeException {

	public ResourceAlreadyExistsException(String resource, String fieldName, String fieldValue) {
		super(String.format("%s with %s : %s already exists", resource, fieldName, fieldValue));
	}
}

package com.dhandapani.ms_loans.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.dhandapani.ms_loans.dto.ErrorResponseDto;

/**
 * @author Dhandapani Sudhakar
 * Centralized Exception Handling - No need for try-catch blocks in Controllers
 */
@RestControllerAdvice
public class MsLoansApplicationGlobalExceptionHanlder {

	/**
	 * Handles ResourceNotFoundException
	 * 
	 * @param ex
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest webRequest) {
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND,
				ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
	}

	/**
	 * Handles ResourceAlreadyExistsException
	 * 
	 * @param ex
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex,
			WebRequest webRequest) {
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false),
				HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
	}

	/**
	 * Handles MethodArgumentNotValidException (Validation) errors
	 * @param ex
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest webRequest) {
		Map<String, String> validationErrors = new HashMap<String, String>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		validationErrors = fieldErrors.stream().collect(
				Collectors.toMap(fieldError -> fieldError.getField(), fieldError -> fieldError.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
	}

	/**
	 * Handles all other Exception other than custom exceptions
	 * 
	 * @param ex
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleException(Exception ex, WebRequest webRequest) {
		ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
	}
}

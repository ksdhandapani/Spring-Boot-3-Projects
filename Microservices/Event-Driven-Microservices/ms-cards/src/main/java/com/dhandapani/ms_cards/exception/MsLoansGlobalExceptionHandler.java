package com.dhandapani.ms_cards.exception;

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

import com.dhandapani.ms_cards.dto.ErrorResponseDto;

@RestControllerAdvice
public class MsLoansGlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(request.getDescription(false), HttpStatus.NOT_FOUND,
				exception.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(
			ResourceAlreadyExistsException exception, WebRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(request.getDescription(false), HttpStatus.BAD_REQUEST,
				exception.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception, WebRequest request) {
		Map<String, String> validationErros = new HashMap<String, String>();
		List<FieldError> fieldErrors = exception.getFieldErrors();
		validationErros = fieldErrors.stream().collect(
				Collectors.toMap(fieldError -> fieldError.getField(), fieldError -> fieldError.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErros);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleException(Exception exception, WebRequest request) {
		ErrorResponseDto errorResponse = new ErrorResponseDto(request.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}

package com.dbms.enquiry.main.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbms.enquiry.main.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentException(MethodArgumentNotValidException ex) {
	    Map<String, String> errorDetails = new HashMap<>();
	    
	    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
	        errorDetails.put(fe.getField(), fe.getDefaultMessage());
	    }
	    
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
	    ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage());
	    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(EnquiryNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleEnquiryNotFoundException(EnquiryNotFoundException e) {
	    ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage());
	    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoEnquiryFoundForStatusException.class)
	public ResponseEntity<ErrorResponseDTO> handleNoEnquiryForStatusException(NoEnquiryFoundForStatusException e) {
	    ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage());
	    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
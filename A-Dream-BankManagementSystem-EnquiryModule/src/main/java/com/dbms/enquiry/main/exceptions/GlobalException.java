package com.dbms.enquiry.main.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
	
		@ExceptionHandler(value = MethodArgumentNotValidException.class)
		public ResponseEntity<ErrorResponseDTO> handleMethodArgumentException(MethodArgumentNotValidException ex) {
		    Map<String, String> invalidFieldDetails = new HashMap<>();
		    
		    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
		        invalidFieldDetails.put(fe.getField(), fe.getDefaultMessage());
		    }
		    
		    ErrorResponseDTO errorResponse = new ErrorResponseDTO("Validation Failed", invalidFieldDetails);
		    
		    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(Exception.class)
		public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
			ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now(), null);
		    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		@ExceptionHandler(EnquiryNotFoundException.class)
		public ResponseEntity<ErrorResponseDTO> handleException(EnquiryNotFoundException e) {
			ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now(), null);
		    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}


package com.dbms.enquiry.main.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponseDTO {
	
	private String message;
	private LocalDateTime timestamp; // Fixed typo (was "timestamb")
	private Map<String, String> fieldErrors; // Added missing field

	// Constructor for validation errors
	public ErrorResponseDTO(String message, Map<String, String> fieldErrors) {
		this.message = message;
		this.timestamp = LocalDateTime.now(); // Automatically set timestamp
		this.fieldErrors = fieldErrors;
	}
}


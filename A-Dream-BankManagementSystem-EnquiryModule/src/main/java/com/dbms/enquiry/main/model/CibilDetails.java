package com.dbms.enquiry.main.model;

import java.time.LocalDateTime;

import com.dbms.enquiry.main.enums.ScoreCategories;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cibilTable")
@Entity
public class CibilDetails {
     
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int cibilId;
	
	private long cibilScore;
	private String remarks;
	
	@Enumerated(EnumType.STRING)
	private ScoreCategories scoreCategories;
	
	
	private LocalDateTime cibilGeneratedDateTime;
	
}

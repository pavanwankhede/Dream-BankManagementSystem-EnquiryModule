package com.dbms.enquiry.main.model;

import java.sql.Time;
import java.util.Date;

import com.dbms.enquiry.main.enums.EnquiryStatus;
import com.dbms.enquiry.main.enums.EnquriyType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "enquiryTable")
@Entity
public class EnquiryDetails {
    
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int enquriyId;
	
	@NotNull(message = "You Can Not Take The Enquiry FullName Is Null !!!")
	@NotBlank(message = "Enquiry FullName Can Not Be Blank")
	 @Size(min = 5, max = 20, message = "Enquiry FullName Must Be Between 3 And 15 Characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Enquiry FullName Must Contain Only Letters And Spaces")
	private String fullName;
	
	 @Min(value = 18, message = "Age Must Be At Least 18/ year. Please Enter Valid Age")
	 @Max(value = 60, message = "Age Must be At Most 60/ year. Please Enter Valid Age")
	private int age;
	 
	 @Email(message = "Invalid email format")
	 @NotBlank(message = "Email is required")
	 private String emailId;
	
	@Min(value = 1000000000L, message = "Enter a valid contact number")
	@Max(value = 9999999999L, message = "Enter a valid contact number")
	private long contactNO;
	
	@Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "PanCard Must Contain Only Letters, Numbers, And Spaces")
	private String panCardNo;
	
	@Enumerated(EnumType.STRING)
	private EnquriyType enquriyType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "enquriy_status", length = 20, nullable = false)
	private EnquiryStatus enquriyStatus;
	
	  @Temporal(TemporalType.DATE)
			private Date enquiryDate;
			
		    @Temporal(TemporalType.TIME)
			private Time enquiryTime;
		    
		    @OneToOne(cascade = CascadeType.MERGE,orphanRemoval = true)
		    private CibilDetails cibilDetails;
	
}

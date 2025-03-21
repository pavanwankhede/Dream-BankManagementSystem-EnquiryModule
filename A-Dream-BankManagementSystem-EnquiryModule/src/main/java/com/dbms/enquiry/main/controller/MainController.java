package com.dbms.enquiry.main.controller;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.enquiry.main.enums.EnquiryStatus;
import com.dbms.enquiry.main.model.EnquiryDetails;
import com.dbms.enquiry.main.serviceInterface.MainServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user-enquiry")
public class MainController {
   
	
	@Autowired
	  private MainServiceInterface serviceInterface;
	
	private static final Logger log= LoggerFactory.getLogger(MainController.class);
	
	@PostMapping("/addEnquiries")
	public ResponseEntity<EnquiryDetails> saveEnquiries(@Valid @RequestBody EnquiryDetails enquiries ){
		
		enquiries.setEnquiryDate(new Date());
		enquiries.setEnquiryTime(new Time(System.currentTimeMillis()));
		
		   
         log.info("Received request to save enquiries: {}", enquiries);
	

         EnquiryDetails enquiry = serviceInterface.saveEnquiriesData(enquiries);
		return new ResponseEntity<>(enquiry, HttpStatus.CREATED);
	}
	@GetMapping("/getAll_Enquiry")
	public ResponseEntity<List<EnquiryDetails>>  getAllEnquiry()
	{
		
		List<EnquiryDetails> listEnquiry=serviceInterface.getAllEnquiry();
		return new ResponseEntity<List<EnquiryDetails>>(listEnquiry,HttpStatus.OK);
		
	}
	
	@GetMapping("/getById/{enquriyId}")
	public ResponseEntity<EnquiryDetails> getEnquiryByID(@PathVariable("enquriyId") int id){
		
		EnquiryDetails enquiryDetails= serviceInterface.getEnquiryByID(id);
		return new ResponseEntity<EnquiryDetails>(enquiryDetails,HttpStatus.OK);
	}
	
	@PatchMapping("/updateEnquiryStatus/{enquriyId}/{enquriyStatus}")
	public ResponseEntity<EnquiryDetails> updateEnquiry(@PathVariable("enquriyId")int id,@PathVariable("enquriyStatus")EnquiryStatus status){
		EnquiryDetails enquiryDetails=serviceInterface.changeEnquiryStatus(id,status);
		return new ResponseEntity<EnquiryDetails>(enquiryDetails,HttpStatus.OK);
	}
}

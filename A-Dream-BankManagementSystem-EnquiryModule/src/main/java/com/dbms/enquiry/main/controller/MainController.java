package com.dbms.enquiry.main.controller;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		log.info("Received Request to update Enquiry Status");
		EnquiryDetails enquiryDetails=serviceInterface.changeEnquiryStatus(id,status);
		return new ResponseEntity<EnquiryDetails>(enquiryDetails,HttpStatus.OK);
	}
	
	
	@GetMapping("/getEnquiryByEnquiryStatus/{enquriyStatus}")
	public ResponseEntity<List<EnquiryDetails>>  getByEnquiryStatus(@PathVariable("enquriyStatus")EnquiryStatus status)
	{
		log.info("Recieved request to find Enquiry by Enquiry status:{}",status);
		List<EnquiryDetails> enquiryBystatus=serviceInterface.getEnquiryByStatus(status);
		log.info("Enquiry by EnquiryStatus:");
		return new ResponseEntity<List<EnquiryDetails>>(enquiryBystatus,HttpStatus.OK);
		
	}
	
	
	@PatchMapping("/updateEnquiryWithCibil/{enquiryId}")
	public ResponseEntity<EnquiryDetails> updateEnquiry(@PathVariable("enquiryId") int enquiryId) {
	    EnquiryDetails updatedEnquiry = serviceInterface.updateSetCibilDetail(enquiryId);
	    return new ResponseEntity<>(updatedEnquiry, HttpStatus.OK);
	}
	
	//To implement paging and sorting
	
	@GetMapping("/enquires")
    public ResponseEntity<Page<EnquiryDetails>> getPaginatedEnquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "enquiryDate") String sortBy) {

        Page<EnquiryDetails> paginatedEnquiries = serviceInterface.getPaginatedEnquiries(page, size, sortBy);
        return ResponseEntity.ok(paginatedEnquiries);
    }

	@PutMapping("/updateOnlyEnquiryDetail/{enquiryId}")
	public ResponseEntity<EnquiryDetails> updateEnquiry(
	        @PathVariable("enquiryId") int enquiryId, 
	        @RequestBody EnquiryDetails updatedEnquiry) {
	    
	    log.info("Received request to update Enquiry with ID: {}", enquiryId);
	    
	    EnquiryDetails updatedDetails = serviceInterface.updateEnquiryDetails(enquiryId, updatedEnquiry);
	    
	    log.info("Successfully updated Enquiry with ID: {}", enquiryId);
	    
	    return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
	}


	  @DeleteMapping("/deleteSpamEnquiries/{enquiryId}")
	    public ResponseEntity<String> deleteSpamEnquiry(@PathVariable("enquiryId") int enquiryId) {
	        log.info("Request received to delete enquiry with ID: {} and status: SPAM", enquiryId);

	        boolean deleted =  serviceInterface.deleteEnquiryByIdAndStatus(enquiryId, EnquiryStatus.SPAM);

	        if (deleted) {
	            return ResponseEntity.ok("Deleted enquiry with ID: " + enquiryId + " and status: SPAM.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("No enquiry with SPAM status found for the given ID.");
	        }
	  }
	  
	  }

	


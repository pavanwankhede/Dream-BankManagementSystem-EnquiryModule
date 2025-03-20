package com.dbms.enquiry.main.serviceImpl;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.dbms.enquiry.main.enums.EnquiryStatus;
import com.dbms.enquiry.main.exceptions.EnquiryNotFoundException;
import com.dbms.enquiry.main.model.CibilDetails;
import com.dbms.enquiry.main.model.EnquiryDetails;
import com.dbms.enquiry.main.repository.CibilRepository;
import com.dbms.enquiry.main.repository.EnquiryRepository;
import com.dbms.enquiry.main.serviceInterface.EmailDetails;
import com.dbms.enquiry.main.serviceInterface.MainServiceInterface;

import jakarta.validation.Valid;

@Service
public class MainServiceImpl implements MainServiceInterface {
	
	private static final Logger log = LoggerFactory.getLogger(MainServiceImpl.class);
	
	  @Autowired
	    private EmailDetails emailDetails;
	
	@Autowired
	private EnquiryRepository enquiryRepository;
	
	@Autowired
	private CibilRepository cibilRepository;

	@Override
	public EnquiryDetails saveEnquiriesData(@Valid EnquiryDetails enquiries) {
		
		 enquiries.setEnquiryDate(new Date());
	        enquiries.setEnquiryTime(new Time(System.currentTimeMillis()));
	             
	       // enquiries.setEnquriyType();
	      //  enquiries.setEnquriyStatus("ACCOUNT_RELATED");
	        
	        // Save CibilDetails if present
	        CibilDetails cibilDetails = enquiries.getCibilDetails();
	        if (cibilDetails != null) {
	        	cibilDetails.setCibilGeneratedDateTime(LocalDateTime.now()); 
	            cibilDetails = cibilRepository.save(cibilDetails);
	            enquiries.setCibilDetails(cibilDetails);
	        }
	        
	        // Save EnquiryDetails
	        EnquiryDetails savedEnquiry = enquiryRepository.save(enquiries);
	        log.info("Enquiries data saved successfully: {}", savedEnquiry);
	        
	        // Send confirmation email
	        if (savedEnquiry != null) {
	            try {
	                emailDetails.sendEnquiryConfirmation(savedEnquiry, savedEnquiry.getCibilDetails());
	                log.info("Enquiry confirmation email sent successfully.");
	            } catch (Exception e) {
	                log.error("Failed to send enquiry confirmation email: {}", e.getMessage(), e);
	            }
	        }
	        
	        return savedEnquiry;
	    }

	@Override
	public List<EnquiryDetails> getAllEnquiry() {
		// TODO Auto-generated method stub
		return enquiryRepository.findAll();
	}

	@Override
	public EnquiryDetails getEnquiryByID(int id) {
		
		Optional<EnquiryDetails> enquiry=enquiryRepository.findById(id);
		
		if (enquiry.isEmpty()) {
			
		throw new EnquiryNotFoundException("Enquiry for the Id- "+id+" Is not Found");
		}
		
		
		return enquiry.get() ;
	}

	@Override
	public EnquiryDetails changeEnquiryStatus(int id, EnquiryStatus status) {
		EnquiryDetails enquiryStatus=getEnquiryByID(id);
		if(enquiryStatus !=null) {
			enquiryStatus.setEnquriyStatus(status);
			
			EnquiryDetails updatedEnquiry = enquiryRepository.save(enquiryStatus);
	        log.info("Enquiry status updated successfully: {}", updatedEnquiry);

	        // Send confirmation email after status update
	        try {
	            // Check if CibilDetails are available and pass them along with the enquiry to the email service
	            emailDetails.sendEnquiryConfirmationStatus(updatedEnquiry, updatedEnquiry.getCibilDetails());
	            log.info("Enquiry status update confirmation email sent successfully.");
	        } catch (Exception e) {
	            log.error("Failed to send enquiry status update email: {}", e.getMessage(), e);
	        }
	        
	        // Return the updated enquiry
	        return updatedEnquiry;
	    } else {
	        log.error("Enquiry with ID " + id + " not found.");
	        throw new EnquiryNotFoundException("Enquiry with ID " + id + " not found.");
	    }
		
		//return enquiryRepository.save(enquiryStatus);
	}
	}












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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dbms.enquiry.main.enums.EnquiryStatus;
import com.dbms.enquiry.main.exceptions.EnquiryNotFoundException;
import com.dbms.enquiry.main.exceptions.NoEnquiryFoundForStatusException;
import com.dbms.enquiry.main.model.CibilDetails;
import com.dbms.enquiry.main.model.CibilScoreUtil;
import com.dbms.enquiry.main.model.EnquiryDetails;
import com.dbms.enquiry.main.repository.CibilRepository;
import com.dbms.enquiry.main.repository.EnquiryRepository;
import com.dbms.enquiry.main.serviceInterface.CibilScoreFetcher;
import com.dbms.enquiry.main.serviceInterface.EmailDetails;
import com.dbms.enquiry.main.serviceInterface.MainServiceInterface;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
	
	@Autowired
	CibilScoreFetcher cibilScoreFetcher;

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
	        	 cibilDetails.setScoreCategories(CibilScoreUtil.getScoreCategory(cibilDetails.getCibilScore()));
	        	 cibilDetails.setRemarks(CibilScoreUtil.getRemarks(cibilDetails.getCibilScore()));
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
	public EnquiryDetails changeEnquiryStatus(int id, EnquiryStatus newStatus) {
	    EnquiryDetails enquiryDetails = getEnquiryByID(id);

	    if (enquiryDetails != null) {
	        // Store previous status before updating
	        EnquiryStatus previousStatus = enquiryDetails.getEnquriyStatus();

	        // Update the status
	        enquiryDetails.setEnquriyStatus(newStatus);
	        EnquiryDetails updatedEnquiry = enquiryRepository.save(enquiryDetails);
	        log.info("Enquiry status updated successfully: {}", updatedEnquiry);

	        // Send status update email with correct previous and new status
	        try {
	            emailDetails.sendEnquiryStatusUpdate(updatedEnquiry, previousStatus, newStatus);
	            log.info("Enquiry status update confirmation email sent successfully.");
	        } catch (Exception e) {
	            log.error("Failed to send enquiry status update email: {}", e.getMessage(), e);
	        }

	        return updatedEnquiry;
	    } else {
	        log.error("Enquiry with ID " + id + " not found.");
	        throw new EnquiryNotFoundException("Enquiry with ID " + id + " not found.");
	    }
	}

	@Override
	public boolean deleteEnquiryByID(int id) {
		 
		if(enquiryRepository.existsById(id))
		{
			enquiryRepository.deleteById(id);
			return true;
		}
		
		return false;
	}
	

	@Override
	public List<EnquiryDetails> getEnquiryByStatus(EnquiryStatus status) {
		List<EnquiryDetails> enquires= enquiryRepository.findByEnquriyStatus(status);
		 if (enquires.isEmpty() ) {
	            throw new NoEnquiryFoundForStatusException("No enquiries found with status: "+status);
	        }
		return enquires;

	}

	 
	@Override
	public EnquiryDetails updateSetCibilDetail(int enquiryId) {
		
	    // Fetch the existing enquiry
        EnquiryDetails existingEnquiry = enquiryRepository.findById(enquiryId)
            .orElseThrow(() -> new RuntimeException("Enquiry with ID " + enquiryId + " not found."));

        // Generate and update CIBIL Score if not already present
        if (existingEnquiry.getCibilDetails() == null) {
            CibilDetails cibilDetails = new CibilDetails();

            // Fetch a random CIBIL score
            Long generatedScore = cibilScoreFetcher.fetchCibilScore(existingEnquiry.getPanCardNo());

            // Set CIBIL details
            cibilDetails.setCibilScore(generatedScore);
            cibilDetails.setScoreCategories(CibilScoreUtil.getScoreCategory(generatedScore));
            cibilDetails.setRemarks(CibilScoreUtil.getRemarks(generatedScore));
            cibilDetails.setCibilGeneratedDateTime(LocalDateTime.now());

            // Attach to EnquiryDetails
            existingEnquiry.setCibilDetails(cibilDetails);
        }

        // Save and return updated enquiry
        return enquiryRepository.save(existingEnquiry);
    }

	@Override
	public Page<EnquiryDetails> getPaginatedEnquiries(int page, int size, String sortBy) {
		
		Pageable pageable = PageRequest.of(page, size,Sort.by(sortBy));
        return enquiryRepository.findAll(pageable);

	}

	@Override
	public EnquiryDetails updateEnquiryDetails(int enquiryId, EnquiryDetails updatedEnquiry) {
		
		log.info("Attempting to update Enquiry with ID: {}", enquiryId);

        Optional<EnquiryDetails> existingEnquiryOpt = enquiryRepository.findById(enquiryId);

        if (!existingEnquiryOpt.isPresent()) {
            log.error("Enquiry with ID {} not found", enquiryId);
            
            //throw new ResourceNotFoundException("Enquiry not found with ID: " + enquiryId);
        }

        EnquiryDetails existingEnquiry = existingEnquiryOpt.get();

        existingEnquiry.setFullName(updatedEnquiry.getFullName());
        existingEnquiry.setEmailId(updatedEnquiry.getEmailId());
        existingEnquiry.setContactNO(updatedEnquiry.getContactNO());
        existingEnquiry.setAge(updatedEnquiry.getAge());
        
        log.info("Existing Enquiry found: {}", existingEnquiry);
          EnquiryDetails savedEnquiry = enquiryRepository.save(existingEnquiry);
        
        log.info("Successfully updated Enquiry with ID: {} | Updated Details: {}", enquiryId, savedEnquiry);

        return savedEnquiry;
	}

	 @Transactional
	    @Override
	    public int deleteEnquiryByIdAndStatus(int enquiryId, EnquiryStatus status) {
	        return enquiryRepository.deleteByIdAndStatus(enquiryId, status);
	    }
	}

	
	
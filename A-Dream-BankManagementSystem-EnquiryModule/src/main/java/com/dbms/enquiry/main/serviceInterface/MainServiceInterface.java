package com.dbms.enquiry.main.serviceInterface;

import java.util.List;

import com.dbms.enquiry.main.enums.EnquiryStatus;
import com.dbms.enquiry.main.model.EnquiryDetails;

import jakarta.validation.Valid;

public interface MainServiceInterface {

 public	EnquiryDetails saveEnquiriesData(@Valid EnquiryDetails enquiries);

public List<EnquiryDetails> getAllEnquiry();

public EnquiryDetails getEnquiryByID(int id);

public EnquiryDetails changeEnquiryStatus(int id, EnquiryStatus status);

public boolean deleteEnquiryByID(int id);

public List<EnquiryDetails> getEnquiryByStatus(EnquiryStatus status);

 

}

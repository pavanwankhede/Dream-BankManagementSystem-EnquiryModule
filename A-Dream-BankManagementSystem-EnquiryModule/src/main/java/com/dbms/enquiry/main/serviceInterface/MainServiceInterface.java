package com.dbms.enquiry.main.serviceInterface;

import com.dbms.enquiry.main.model.EnquiryDetails;

import jakarta.validation.Valid;

public interface MainServiceInterface {

 public	EnquiryDetails saveEnquiriesData(@Valid EnquiryDetails enquiries);

}

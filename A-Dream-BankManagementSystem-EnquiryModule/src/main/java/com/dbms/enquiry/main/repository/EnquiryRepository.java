package com.dbms.enquiry.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbms.enquiry.main.model.EnquiryDetails;

@Repository
public interface EnquiryRepository extends JpaRepository<EnquiryDetails, Integer> {

}

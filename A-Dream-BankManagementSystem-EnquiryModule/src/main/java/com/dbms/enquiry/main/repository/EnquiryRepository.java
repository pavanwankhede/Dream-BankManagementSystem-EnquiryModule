package com.dbms.enquiry.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbms.enquiry.main.enums.EnquiryStatus;
import com.dbms.enquiry.main.model.EnquiryDetails;

@Repository
public interface EnquiryRepository extends JpaRepository<EnquiryDetails, Integer> {

	List<EnquiryDetails> findByEnquriyStatus(EnquiryStatus status);

	Page<EnquiryDetails> findAll(Pageable pageable);

	int deleteByEnquriyStatus(String string);

}

package com.dbms.enquiry.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dbms.enquiry.main.enums.EnquiryStatus;
import com.dbms.enquiry.main.model.EnquiryDetails;

@Repository
public interface EnquiryRepository extends JpaRepository<EnquiryDetails, Integer> {

	List<EnquiryDetails> findByEnquriyStatus(EnquiryStatus status);

	Page<EnquiryDetails> findAll(Pageable pageable);
	
	@Modifying
	@Query("DELETE FROM EnquiryDetails e WHERE e.enquriyId = :enquiryId AND e.enquriyStatus = :status")
	int deleteByIdAndStatus(@Param("enquiryId") int enquiryId, @Param("status") EnquiryStatus status);


}

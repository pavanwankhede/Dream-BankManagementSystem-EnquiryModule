package com.dbms.enquiry.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan
@EnableDiscoveryClient
@SpringBootApplication
public class ADreamBankManagementSystemEnquiryModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ADreamBankManagementSystemEnquiryModuleApplication.class, args);
	}

}

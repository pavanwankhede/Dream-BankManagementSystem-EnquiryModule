package com.dbms.enquiry.main.serviceInterface;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class CibilScoreFetcher {
	
	public long fetchCibilScore(String panCardNo) {
        Random random = new Random();
        return 300 + random.nextInt(601);  // Random score between 300 and 900
    }


}

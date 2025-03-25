package com.dbms.enquiry.main.model;

import com.dbms.enquiry.main.enums.ScoreCategories;

public class CibilScoreUtil {
	

	/*
	 * public static long generateRandomCibilScore() { Random random = new Random();
	 * return 300 + random.nextInt(601); // Random score between 300 and 900 }
	 */

     
	public static ScoreCategories getScoreCategory(long score) {
		if (score >= 900) return ScoreCategories.EXCELLENT;
		else if (score >= 700) return ScoreCategories.GOOD;
		else if (score >= 550) return ScoreCategories.AVERAGE;
		else return ScoreCategories.POOR;
	}
	
	  public static String getRemarks(long score) {
	        if (score >= 900) return "Great credit history! Eligible for best offers.";
	        else if (score >= 700) return "Good credit history. Considered for most loans.";
	        else if (score >= 550) return "Average credit history. May need improvement.";
	        else return "Poor credit history. High chance of rejection.";
	    }
}

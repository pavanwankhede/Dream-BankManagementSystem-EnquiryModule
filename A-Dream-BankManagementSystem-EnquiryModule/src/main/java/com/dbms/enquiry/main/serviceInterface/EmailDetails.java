package com.dbms.enquiry.main.serviceInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dbms.enquiry.main.model.CibilDetails;
import com.dbms.enquiry.main.model.EnquiryDetails;


@Service
public class EmailDetails {
	
	   private static final Logger log = LoggerFactory.getLogger(EmailDetails.class);
	    
	    @Autowired
	    private JavaMailSender sender;
	    
	    @Value("${spring.mail.username}")
	    private String FROM_MAIL;

	    public void sendEnquiryConfirmation(EnquiryDetails enquiry, CibilDetails cibil) {
	    	
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(FROM_MAIL);
	        message.setTo(enquiry.getEmailId());
	        message.setSubject("Enquiry Confirmation - " + enquiry.getEnquriyId());
	        message.setText(
	        	    "Dear " + enquiry.getFullName() + ",\n\n" +
	        	    
	        	    "We are pleased to inform you that your enquiry has been **approved**. Below are your details:\n\n" +
	                
	        	    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "📌 Enquiry Details\n" +
	        	    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "🆔 Enquiry ID: " + enquiry.getEnquriyId() + "\n" +
	        	    "📞 Contact No: " + enquiry.getContactNO() + "\n" +
	        	    "📄 PAN Card: " + enquiry.getPanCardNo() + "\n" +
	        	    "📋 Enquiry Type: " + enquiry.getEnquriyType() + "\n" +
	        	    "📅 Enquiry Date: " + enquiry.getEnquiryDate() + "\n" +
	        	    "⏰ Enquiry Time: " + enquiry.getEnquiryTime() + "\n" +
	        	    "📊 Enquiry Status: " + enquiry.getEnquriyStatus() + "\n\n" +
	        	    
	        	    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "📌 CIBIL Details\n" +
	        	    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "🆔 CIBIL ID: " + cibil.getCibilId() + "\n" +
	        	    "📊 CIBIL Score: " + cibil.getCibilScore() + "\n" +
	        	    "📂 Score Category: " + cibil.getScoreCategories() + "\n" +
	        	    "📝 Remarks: " + cibil.getRemarks() + "\n\n" +
	        	    
	        	    "📢 Note: If you have any questions or require further assistance, please feel free to contact our support team.\n\n" +
	        	    
	        	    "Best Regards,\n" +
	        	    "📧 Customer Support Team\n" +
	        	    "🏦 BankFinancial Services\n\n" +
	        	    
	        	    "✨ Thank You for Choosing Us! ✨"
	        	);
	        
	        sender.send(message);
	        
	        log.info("Enquiry confirmation email sent to {}", enquiry.getEmailId());
	    }
	    
public void sendEnquiryConfirmationStatus(EnquiryDetails enquiry, CibilDetails cibil) {
	    	
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(FROM_MAIL);
	        message.setTo(enquiry.getEmailId());
	        message.setSubject("Enquiry Confirmation - " + enquiry.getEnquriyId());
	        message.setText(
	        	    "Dear " + enquiry.getFullName() + ",\n\n" +
	        	    
	        	    "Thank you for your enquiry with us! Below are your details:\n\n" +
	        	    
	        	    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "📌 Enquiry Details\n" +
	        	    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "🆔 Enquiry ID: " + enquiry.getEnquriyId() + "\n" +
	        	    "📞 Contact No: " + enquiry.getContactNO() + "\n" +
	        	    "📄 PAN Card: " + enquiry.getPanCardNo() + "\n" +
	        	    "📋 Enquiry Type: " + enquiry.getEnquriyType() + "\n" +
	        	    "📅 Enquiry Date: " + enquiry.getEnquiryDate() + "\n" +
	        	    "⏰ Enquiry Time: " + enquiry.getEnquiryTime() + "\n" +
	        	    "📊 Enquiry Status: " + enquiry.getEnquriyStatus() + "\n\n" +
	        	    
	        	    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "📌 CIBIL Details\n" +
	        	    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "🆔 CIBIL ID: " + cibil.getCibilId() + "\n" +
	        	    "📊 CIBIL Score: " + cibil.getCibilScore() + "\n" +
	        	    "📂 Score Category: " + cibil.getScoreCategories() + "\n" +
	        	    "📝 Remarks: " + cibil.getRemarks() + "\n\n" +
	        	    
	        	    "📢 Note: If you have any questions or require further assistance, please feel free to contact our support team.\n\n" +
	        	    
	        	    "Best Regards,\n" +
	        	    "📧 Customer Support Team\n" +
	        	    "🏦 BankFinancial Services\n\n" +
	        	    
	        	    "✨ Thank You for Choosing Us! ✨"
	        	);
	        
	        sender.send(message);
	        
	        log.info("Enquiry confirmation email sent to {}", enquiry.getEmailId());
	    }
	}
	



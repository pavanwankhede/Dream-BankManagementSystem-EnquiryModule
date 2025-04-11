package com.dbms.enquiry.main.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.dbms.enquiry.main.enums.EnquiryStatus;
import com.dbms.enquiry.main.model.EnquiryDetails;

@Service
public class EmailDetails {
	
	   private static final Logger log = LoggerFactory.getLogger(EmailDetails.class);
	    
	    @Autowired
	    private JavaMailSender sender;
	    
	    @Value("${spring.mail.username}")
	    private String FROM_MAIL;

	    public void sendEnquiryConfirmation(EnquiryDetails enquiry) {
	    	
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(FROM_MAIL);
	        message.setTo(enquiry.getEmailId());
	        message.setSubject("Enquiry Confirmation - " + enquiry.getEnquriyId());
	        message.setText(
	        	    "Dear " + enquiry.getFullName() + ",\n\n" +
	        	    
	        	    "Thank you for your enquiry with us!  Below are your details:\n\n" +
	        	   // "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "📌 Enquiry Details : \n" +
	                  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "🆔 Enquiry ID: " + enquiry.getEnquriyId() + "\n" +
	        	    "📞 Contact No: " + enquiry.getContactNO() + "\n" +
	        	    "📄 PAN Card: " + enquiry.getPanCardNo() + "\n" +
	        	    "📋 Enquiry Type: " + enquiry.getEnquriyType() + "\n" +
	        	    "📅 Enquiry Date: " + enquiry.getEnquiryDate() + "\n" +
	        	    "⏰ Enquiry Time: " + enquiry.getEnquiryTime() + "\n" +
	        	    "📊 Enquiry Status: " + enquiry.getEnquriyStatus() + "\n\n" +
	        	    
	        	/*//    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "📌 CIBIL Details : \n" +
	                  "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	        	    "🆔 CIBIL ID: " + cibil.getCibilId() + "\n" +
	        	    "📊 CIBIL Score: " + cibil.getCibilScore() + "\n" +
	        	    "📂 Score Category: " + cibil.getScoreCategories() + "\n" +
	        	    "📝 Remarks: " + cibil.getRemarks() + "\n\n" +*/
	        	    
	        	    "Our team will review your request and get back to you as soon as possible.ANd We appreciate your interest and look forward to assisting you!.\n"+
	        	    "📢 Note: If you have any questions or require further assistance, please feel free to contact our support team.\n\n" +
	        	    
	        	    "Best Regards,\n" +
	        	    "📧 Customer Support Team\n" +
	        	    "🏦 BankFinancial Services\n\n" +
	        	    
	        	    "✨ Thank You for Choosing Us! ✨"
	        	);
	        
	        sender.send(message);
	        
	        log.info("Enquiry confirmation email sent to {}", enquiry.getEmailId());
	    }
	    
	    public void sendEnquiryStatusUpdate(EnquiryDetails enquiry, EnquiryStatus previousStatus, EnquiryStatus newStatus) {
	    	  SimpleMailMessage message = new SimpleMailMessage();
	    	    message.setFrom(FROM_MAIL);
	    	    message.setTo(enquiry.getEmailId());
	    	    message.setSubject("Enquiry Status Update - " + enquiry.getEnquriyId());

	    	    String emailContent;

	    	    if (newStatus == EnquiryStatus.APPROVED) {
	    	        emailContent = 
	    	            "Dear " + enquiry.getFullName() + ",\n\n" +
	    	            "We would like to inform you that your enquiry status has been updated.\n" +
	    	            "🎉 Congratulations!! 🎉 You are eligible for a loan.\n\n" +
	    	            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	    	            "📌 Enquiry Status Update\n" +
	    	            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	    	            "🆔 Enquiry ID: " + enquiry.getEnquriyId() + "\n" +
	    	            "📞 Contact No: " + enquiry.getContactNO() + "\n" +
	    	            "📋 Previous Status: " + previousStatus + "\n" +
	    	            "✅ New Status: " + newStatus + "\n\n" +
	    	            "📢 Next Steps: Our team will contact you shortly with further details on loan processing.\n\n" +
	    	            "Best Regards,\n" +
	    	            "📧 Customer Support Team\n" +
	    	            "🏦 BankFinancial Services\n\n" +
	    	            "✨ Thank You for Choosing Us! ✨";
	    	    } else if (newStatus == EnquiryStatus.REJECTED) {
	    	        emailContent = 
	    	            "Dear " + enquiry.getFullName() + ",\n\n" +
	    	            "We regret to inform you that your enquiry has been **rejected** after review.\n\n" +
	    	            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	    	            "📌 Enquiry Status Update\n" +
	    	            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	    	            "🆔 Enquiry ID: " + enquiry.getEnquriyId() + "\n" +
	    	            "📞 Contact No: " + enquiry.getContactNO() + "\n" +
	    	            "📋 Previous Status: " + previousStatus + "\n" +
	    	            "❌ New Status: " + newStatus + "\n\n" +
	    	            "📢 Possible Reasons: This could be due to low CIBIL score, incomplete documents, or other eligibility criteria.\n" +
	    	            "💡 Next Steps: You may reapply after improving your eligibility or contact our support team for further details.\n\n" +
	    	            "Best Regards,\n" +
	    	            "📧 Customer Support Team\n" +
	    	            "🏦 BankFinancial Services\n\n" +
	    	            "🔄 We Appreciate Your Interest & Look Forward to Serving You Again! 🔄";
	    	    } else if (newStatus == EnquiryStatus.SPAM) {
	    	        emailContent = 
	    	            "Dear " + enquiry.getFullName() + ",\n\n" +
	    	            "We would like to inform you that your enquiry has been marked as **SPAM** in our system.\n\n" +
	    	            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	    	            "📌 Enquiry Status Update\n" +
	    	            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	    	            "🆔 Enquiry ID: " + enquiry.getEnquriyId() + "\n" +
	    	            "📞 Contact No: " + enquiry.getContactNO() + "\n" +
	    	            "📋 Previous Status: " + previousStatus + "\n" +
	    	            "🚨 New Status: " + newStatus + "\n\n" +
	    	            "📢 Why was this marked as SPAM?\n" +
	    	            "- Your enquiry may contain **incomplete, duplicate, or misleading information**.\n" +
	    	            "- It might have triggered our **fraud detection system**.\n\n" +
	    	            "⚠️ **What You Can Do?**\n" +
	    	            "- If you believe this is an error, please **contact our support team immediately**.\n" +
	    	            "- Submit a new enquiry with **correct and verifiable details**.\n\n" +
	    	            "Best Regards,\n" +
	    	            "📧 Customer Support Team\n" +
	    	            "🏦 BankFinancial Services\n\n" +
	    	            "🔹 Ensuring Secure & Reliable Financial Services 🔹";
	    	    } else {
	    	        emailContent = 
	    	            "Dear " + enquiry.getFullName() + ",\n\n" +
	    	            "We would like to inform you that your enquiry status has been updated.\n\n" +
	    	            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	    	            "📌 Enquiry Status Update\n" +
	    	            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
	    	            "🆔 Enquiry ID: " + enquiry.getEnquriyId() + "\n" +
	    	            "📞 Contact No: " + enquiry.getContactNO() + "\n" +
	    	            "📋 Previous Status: " + previousStatus + "\n" +
	    	            "🔄 New Status: " + newStatus + "\n\n" +
	    	            "📢 If you have any questions, feel free to reach out to our support team.\n\n" +
	    	            "Best Regards,\n" +
	    	            "📧 Customer Support Team\n" +
	    	            "🏦 BankFinancial Services\n\n" +
	    	            "✨ Thank You for Choosing Us! ✨";
	    	    }

	    	    message.setText(emailContent);
	    	    sender.send(message);
	    	    log.info("Enquiry status update email sent to {}", enquiry.getEmailId());
	    }
}


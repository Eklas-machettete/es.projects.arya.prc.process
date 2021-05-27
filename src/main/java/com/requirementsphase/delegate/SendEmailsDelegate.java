package com.requirementsphase.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.requirementsphase.services.MailService;

@Service("sendEmails")
public class SendEmailsDelegate implements ExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailsDelegate.class);
	@Autowired
    MailService mailService;
	public void notify(DelegateExecution execution) throws Exception {
		LOGGER.info("SendEmailsDelegate");
		
		String nameMeeting=(String)execution.getVariable("nameMeeting");
		String dateMeeting=(String)execution.getVariable("dateMeeting");
		String nahourMeeting=(String)execution.getVariable("hourMeeting");
		String descriptionMeeting=(String)execution.getVariable("descriptionMeeting");
		
		String sub="Meeting "+nameMeeting+" on "+dateMeeting+" at "+nahourMeeting;
		String body=descriptionMeeting;
		String[] emailList=new String[] {"eklasurrahman786@gmail.com"} ;
		try {
			LOGGER.info("Try to Sending Mail");
			 mailService.sendEmail(sub,body,emailList);
		     mailService.sendEmailWithAttachment(sub,body,emailList);
		     LOGGER.info("Email sent successfuly");
		     
		     
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			LOGGER.info("Email sent fail");
		}
	  
	 	
		
		
		
		
		
		
		
		
		
		
		
	}

}

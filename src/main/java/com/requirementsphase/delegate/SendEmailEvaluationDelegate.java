package com.requirementsphase.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.requirementsphase.services.MailService;

@Service("sendEmailEvaluation")
public class SendEmailEvaluationDelegate implements ExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailEvaluationDelegate.class);
	@Autowired
    MailService mailService;
	public void notify(DelegateExecution execution) throws Exception {
		LOGGER.info("SendEmailEvaluationDelegate");
		try {
			LOGGER.info("Try to Sending Mail");
			mailService.sendEmail("sub","body",new String[] {"eklasurrahman786@gmail.com"});
		    mailService.sendEmailWithAttachment("sub","body",new String[] {"eklasurrahman786@gmail.com", "eklasrh@yahoo.com"});
		    LOGGER.info("Email sent successfuly");
			
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			LOGGER.info("Email sent fail");

		}
		
			
			
		
	}
}

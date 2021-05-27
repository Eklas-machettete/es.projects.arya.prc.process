package com.requirementsphase;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class RequirementsPhaseApplication {
	
	@Autowired
	private ManagementService managementService;

	public static void main(String... args) {
	    SpringApplication.run(RequirementsPhaseApplication.class, args);
	}
	
	@PostConstruct
	public void startProcess() {
		managementService.toggleTelemetry(false);
	}

}

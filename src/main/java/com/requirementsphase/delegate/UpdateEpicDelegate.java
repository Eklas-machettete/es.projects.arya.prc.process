package com.requirementsphase.delegate;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.spin.json.SpinJsonNode;
import static org.camunda.spin.Spin.JSON;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("updateEpic")
public class UpdateEpicDelegate implements JavaDelegate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailEvaluationDelegate.class);
	@Value("${jira.host}")
	private String jiraHost;
	@Value("${jira.projectKey}")
	private String projectKey;
	@Value("${reporter.id}")
	String reporterId;
	
	public void execute(DelegateExecution execution) throws Exception {

		LOGGER.info(execution.getCurrentActivityName());
		String summary=(String)execution.getVariable("summaryProposal");
		String description=(String)execution.getVariable("descriptionProposal");
		String name=(String)execution.getVariable("nameProposal");
		String priority=(String)execution.getVariable("priorityProposal");
		String tags=(String)execution.getVariable("tagsProposal");
		
		execution.setVariable("reportProposal", reporterId);
		String reporter=(String)execution.getVariable("reportProposal");
		System.out.println(reporter);
		RestTemplate restTemplate = new RestTemplate();
        String url = jiraHost+"createIssue";
		System.out.println(url);
		String requestJson = "{\"fields\":{\"project\":{\"key\":\""+projectKey+ "\"},\"summary\":\""+summary+"\",\"customfield_10011\":\""+name+"\",\"priority\":{\"name\":\""+priority+"\"},\"labels\":[\""+tags+"\"],\"reporter\":{\"id\":\""+reporter+"\"},\"description\":{\"type\":\"doc\",\"version\":1,\"content\":[{\"type\":\"paragraph\",\"content\":[{\"type\":\"text\",\"text\":\""+description+"\"}]}]},\"issuetype\":{\"name\":\"Epic\"}}}";
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        try {
    		String answer = restTemplate.postForObject(url, entity, String.class);
    		LOGGER.info(answer);
    		LOGGER.info("Successfully wrote data at es.jira.qubist.sys.service");
		} catch (Exception e) {
			
			LOGGER.info(e.getMessage());
			LOGGER.info("Fail to write data at es.jira.qubist.sys.service");
		}
		
		List<String> list = new ArrayList<>();
		ProcessEngine processEngine = execution.getProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();
		List<User> managementUsers = identityService.createUserQuery()
			      .list();
		for (User user : managementUsers) {
			LOGGER.info(user.getId());
			list.add(user.getId());
		}
		SpinJsonNode jsonNode = JSON("[]");
	    execution.setVariable("reasonList", jsonNode);
		execution.setVariable("collection", list);
		execution.setVariable("rejectedCount", 0);
	}
	
}

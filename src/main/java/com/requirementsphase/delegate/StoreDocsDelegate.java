package com.requirementsphase.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("storeDocs")
public class StoreDocsDelegate implements JavaDelegate {
	@Value("${arydb.host}")
	String aryadbHost;
	@Value("${reporter.id}")
	String reporterId;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailEvaluationDelegate.class);
	
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info(execution.getCurrentActivityName());
		RestTemplate restTemplate = new RestTemplate();
        String url = aryadbHost+"insertStory";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        Integer ind = (Integer) execution.getVariable("INDEX");
            //System.out.println("INDEX: "+ind);
			for(int i = 0; i < ind; i++)
			{	String nameDocRegistry= (String) execution.getVariable("nameDocRegistry"+i);
				String summaryDocRegistry = (String) execution.getVariable("summaryDocRegistry"+i);
				String descriptionDocRegistry = (String) execution.getVariable("descriptionDocRegistry"+i);
			    String priorityDocRegistry=(String)execution.getVariable("priorityDocRegistry"+i);
			    String tagsDocRegistry=(String)execution.getVariable("tagsDocRegistry"+i);
				//String reportDocRegistry=(String)execution.getVariable("reportDocRegistry"+i);
				String reportDocRegistry=reporterId;
                String requestJson="{\"name\":\""+nameDocRegistry+"\",\"summary\":\""+summaryDocRegistry+"\",\"description\":\""+descriptionDocRegistry+"\",\"owner\":\""+reportDocRegistry+"\",\"priority\":\""+priorityDocRegistry+"\",\"assigned\":\""+tagsDocRegistry+"\"}";
		     	HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
		     	try{
		     		String answer = restTemplate.postForObject(url, entity, String.class);
                    LOGGER.info(answer);
                    LOGGER.info("Successfully wrote data at es.aryadb.qubist.sys.service");
				}
		     	catch (Exception e){
					LOGGER.info(e.getMessage());
					LOGGER.info("Fail to write data at es.aryadb.qubist.sys.service");
                }
				
				
				
			}
	  
		}

}
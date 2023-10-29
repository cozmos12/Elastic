package com.example.demo;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
@EnableElasticsearchRepositories()
@EnableProcessApplication


public class DemoApplication {

	@Lazy
	@Autowired
	private ProcessEngine processEngine;




	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@PostConstruct
	private void processPostDeploy() throws IOException {

		File initialFile = new File("src/main/resources/BPMN.xml");

		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();

		RepositoryService repositoryService = processEngine.getRepositoryService();
		InputStream stream =  FileUtils.openInputStream(initialFile);
		DeploymentEntity deploymentResult;

		String deploiementBuild = initialFile.getName() + "deploiement";
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(deploiementBuild);
		String definitionName = initialFile.getName() + ".bpmn";

		deploymentBuilder.addInputStream(definitionName, stream);
		String deploymentId = deploymentBuilder.deploy().getId();
		Map<String, Object> variables = new HashMap<>();
		variables.put("deploymentId", deploymentId);
		log.info("depoiement -------------> {}", deploymentId);

		List definitions = repositoryService.createProcessDefinitionQuery().list();
		log.info("depoiement size ------------> {} ",definitions.size());

	}

}

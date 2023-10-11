package com.example.demo;

import com.example.demo.util.converter.ListToJson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages ="com.example.demo.elasticSearch.repository" )

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

package com.example.demo.service;


import com.example.demo.mapper.Mapper;
import com.example.demo.dto.ProductDto;
import com.example.demo.repository.ProductRepository;
import com.example.demo.entitiy.Product;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    private ProcessEngine processEngine;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public void Save(ProductDto productDto){
        RuntimeService runtimeService= processEngine.getRuntimeService();
        TaskService taskService= processEngine.getTaskService();
        ProcessInstance i = runtimeService.startProcessInstanceByKey("Process_19ke8mm", Map.of("hello", productDto.getId()));

        Product product = Mapper.getProduct(productDto);
        productRepository.save(product);
    }

}


















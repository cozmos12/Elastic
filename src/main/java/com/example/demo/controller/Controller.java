package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")

public class Controller {

    private final ProductService productService;
    @Autowired
    TaskService taskService;


    public Controller(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping("/all")
    public List<Map<String, Object>> findAll(){
        return taskService.createTaskQuery().processDefinitionKey("Process_19ke8mm").active().list().stream().map(
                task -> {
                    Map<String, Object> taskAsMap = new HashMap<>();
                    taskAsMap.put("id", task.getId());
                    taskAsMap.put("name", task.getName());
                    taskAsMap.put("description", task.getDescription());
                    taskAsMap.put("time", task.getCreateTime());
                    final Map<String, Object> vars = taskService.getVariables(task.getId());
                    taskAsMap.put("vars", vars);

                    return taskAsMap;
                }
        ).collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public void executeTask(@PathVariable String id, @RequestBody Map<String, Object> vars) {
        taskService.complete(id, vars);
    }
    @PostMapping("/save")
    public void save(@RequestBody ProductDto productDto){
        productService.Save(productDto);
    }
}
